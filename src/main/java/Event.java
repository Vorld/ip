import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task{
    public static final String SHORT_HAND = "E";
    
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event(String description, boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }


    @Override
    public String toString() {
        return String.format("[%s]%s (from: %s to: %s)", SHORT_HAND, super.toString(), this.from.format(OUTPUT_FORMATTER), this.to.format(OUTPUT_FORMATTER));
    }

    @Override
    public String saveString() {
        return String.format("%s | %s | %s | %s", "E", super.saveString(), this.from.format(INPUT_FORMATTER), this.to.format(INPUT_FORMATTER));
    }
}
