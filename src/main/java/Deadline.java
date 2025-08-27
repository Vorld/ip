import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    public static final String SHORT_HAND = "D";

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
    }


    @Override
    public String toString() {
        return String.format("[%s]%s (by: %s)", SHORT_HAND, super.toString(), this.by.format(OUTPUT_FORMATTER));
    }

    @Override
    public String saveString() {
        return String.format("%s | %s | %s", SHORT_HAND, super.saveString(), this.by.format(INPUT_FORMATTER));
    }
}
