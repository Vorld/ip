import java.time.LocalDateTime;
import java.util.ArrayList;

public class Chuck {

    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        Storage storage = new Storage();
        TaskList tasks = new TaskList();
        
        try {
            tasks = storage.loadTasks();
        } catch (ChuckException ce) {
            ui.showMessage("Error loading tasks: " + ce.getMessage() + "\nStarting with empty task list.");
        }

        while (true) {
            try {
                String input = ui.readCommand();
                Command command = Parser.parseCommand(input);

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
                    for (int i = 1; i <= tasks.size(); i++) {
                        listMessage.append(i).append(".").append(tasks.get(i)).append("\n");
                    }
                    ui.showMessage(listMessage.toString().trim());
                    break;
                }
                case DELETE: {
                    String[] commandArgs = Parser.parseArguments(input, command);
                    int taskNumber = Integer.parseInt(commandArgs[0]);

                    Task deletedTask = tasks.get(taskNumber);
                    tasks.delete(taskNumber);
                    ui.showMessage("Noted. I've removed this task:\n" + deletedTask);
                    break;
                }
                case MARK: {
                    String[] commandArgs = Parser.parseArguments(input, command);
                    int taskNumber = Integer.parseInt(commandArgs[0]);

                    tasks.get(taskNumber).markDone();
                    ui.showMessage("Nice! I've marked this task as done:\n" + tasks.get(taskNumber));
                    break;
                }
                case UNMARK: {
                    String[] commandArgs = Parser.parseArguments(input, command);
                    int taskNumber = Integer.parseInt(commandArgs[0]);

                    tasks.get(taskNumber).unmarkDone();
                    ui.showMessage("OK, I've marked this task as not done yet:\n" + tasks.get(taskNumber));
                    break;
                }
                case TODO: {
                    String[] commandArgs = Parser.parseArguments(input, command);
                    String description = commandArgs[0];

                    if (description.isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    tasks.add(new Todo(description));
                    Task addedTask = tasks.get(tasks.size());
                    ui.showMessage("Got it. I've added this task:\n" + addedTask + "\nNow you have " + tasks.size() + " tasks in the list.");
                    break;
                }
                case DEADLINE: {
                    String rest = input.substring(8).trim();

                    if (!rest.contains("/by ")) {
                        throw new ChuckException("Ensure you have a /by date for deadline tasks!");
                    }

                    String[] commandArgs = Parser.parseArguments(input, command);
                    String description = commandArgs[0];
                    String by = commandArgs[1];

                    if (description.isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    if (by.isEmpty()) {
                        throw new ChuckException("Oops! Your by date can't be empty :(");
                    }

                    LocalDateTime byDateTime = Parser.parseDateTime(by);
                    tasks.add(new Deadline(description, byDateTime));
                    Task addedTask = tasks.get(tasks.size());
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

                    String[] commandArgs = Parser.parseArguments(input, command);
                    String description = commandArgs[0];
                    String from = commandArgs[1];
                    String to = commandArgs[2];

                    if (description.isEmpty()) {
                        throw new ChuckException("Oops! Your description can't be empty :(");
                    }

                    if (from.isEmpty()) {
                        throw new ChuckException("Oops! Your from date can't be empty :(");
                    }
                    if (to.isEmpty()) {
                        throw new ChuckException("Oops! Your to date can't be empty :(");
                    }

                    LocalDateTime fromDateTime = Parser.parseDateTime(from);
                    LocalDateTime toDateTime = Parser.parseDateTime(to);
                    tasks.add(new Event(description, fromDateTime, toDateTime));
                    Task addedTask = tasks.get(tasks.size());
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
