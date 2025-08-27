import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Chuck {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static LocalDateTime parseDateTime(String dateTimeString) throws ChuckException {
        try {
            return LocalDateTime.parse(dateTimeString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ChuckException("Invalid date format! Use yyyy-MM-dd HH:mm");
        }
    }

    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        ArrayList<Task> tasks = new ArrayList<>();
        Storage storage = new Storage();
        tasks = storage.loadTasks();

        while (true) {
            try {
                String input = ui.readCommand();
                Command command = Command.fromString(input);

                if (command == null) {
                    throw new ChuckException("Oops! That's not a real Chuck command!");
                }

                switch (command) {
                case BYE: {
                    ui.showMessage("See you around!");
                    return;
                }
                case LIST:{
                    StringBuilder listMessage = new StringBuilder("Here are the tasks in your list:\n");
                    for (int i = 0; i < tasks.size(); i++) {
                        listMessage.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
                    }
                    ui.showMessage(listMessage.toString().trim());
                    break;
                }
                case DELETE: {
                    String taskNumberStr = input.substring(7);
                    int taskNumber = Integer.parseInt(taskNumberStr);

                    Task deletedTask = tasks.get(taskNumber - 1);
                    tasks.remove(taskNumber - 1);
                    ui.showMessage("Noted. I've removed this task:\n" + deletedTask);
                    break;
                }
                case MARK: {
                    String taskNumberStr = input.substring(5);
                    int taskNumber = Integer.parseInt(taskNumberStr);

                    tasks.get(taskNumber - 1).markDone();
                    ui.showMessage("Nice! I've marked this task as done:\n" + tasks.get(taskNumber - 1));
                    break;
                }
                case UNMARK: {
                    String taskNumberStr = input.substring(7);
                    int taskNumber = Integer.parseInt(taskNumberStr);

                    tasks.get(taskNumber - 1).unmarkDone();
                    ui.showMessage("OK, I've marked this task as not done yet:\n" + tasks.get(taskNumber - 1));
                    break;
                }
                case TODO: {
                    String description = input.substring(4).trim();

                    if (description.isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    tasks.add(new Todo(description));
                    Task addedTask = tasks.get(tasks.size() - 1);
                    ui.showMessage("Got it. I've added this task:\n" + addedTask + "\nNow you have " + tasks.size() + " tasks in the list.");
                    break;
                }
                case DEADLINE: {
                    String rest = input.substring(8).trim();

                    if (!rest.contains("/by ")) {
                        throw new ChuckException("Ensure you have a /by date for deadline tasks!");
                    }

                    String description = rest.substring(0, rest.indexOf("/by ")).trim();

                    if (description.isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    String by = rest.substring(rest.indexOf("/by ") + 4).trim();

                    if (by.isEmpty()) {
                        throw new ChuckException("Oops! Your by date can't be empty :(");
                    }

                    LocalDateTime byDateTime = parseDateTime(by);
                    tasks.add(new Deadline(description, byDateTime));
                    Task addedTask = tasks.get(tasks.size() - 1);
                    ui.showMessage("Got it. I've added this task:\n" + addedTask + "\nNow you have " + tasks.size() + " tasks in the list.");
                    break;
                }
                case EVENT: {
                    String rest = input.substring(5).trim();

                    if (!rest.contains("/from ")) {
                        throw new ChuckException("Ensure you have a /from date for event tasks!");
                    }
                    if (!rest.contains("/to ")) {
                        throw new ChuckException("Ensure you have a /to date for event tasks!");
                    }

                    String description = rest.substring(0, rest.indexOf("/from ")).trim();

                    if (description.isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    String from = rest.substring(rest.indexOf("/from ") + 6, rest.indexOf("/to ")).trim();
                    String to = rest.substring(rest.indexOf("/to ") + 4).trim();

                    if (from.isEmpty()) {
                        throw new ChuckException("Oops! Your from date can't be empty :(");
                    }
                    if (to.isEmpty()) {
                        throw new ChuckException("Oops! Your to date can't be empty :(");
                    }

                    LocalDateTime fromDateTime = parseDateTime(from);
                    LocalDateTime toDateTime = parseDateTime(to);
                    tasks.add(new Event(description, fromDateTime, toDateTime));
                    Task addedTask = tasks.get(tasks.size() - 1);
                    ui.showMessage("Got it. I've added this task:\n" + addedTask + "\nNow you have " + tasks.size() + " tasks in the list.");
                    break;
                } case SAVE: {
                    storage.saveTasks(tasks);
                    ui.showMessage("Saved your tasks to hard disk!");
                }
                }

            } catch (ChuckException ce) {
                ui.showMessage(ce.getMessage());
            }
        }

    }
}
