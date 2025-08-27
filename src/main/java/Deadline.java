import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task{
    private static final String SHORT_HAND = "D";
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, boolean isDone, String by) {
        super(description, isDone);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (by: %s)", SHORT_HAND, super.toString(), this.by);
    }

    @Override
    public String saveString() {
        return String.format("%s | %s | %s", SHORT_HAND, super.saveString(), this.by);
    }
}
