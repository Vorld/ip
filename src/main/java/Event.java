import java.time.LocalDateTime;

public class Event extends Task{
    public static final String SHORT_HAND = "E";
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
        return String.format("[%s]%s (from: %s to: %s)", SHORT_HAND, super.toString(), Parser.formatDateTime(this.from), Parser.formatDateTime(this.to));
    }

    @Override
    public String saveString() {
        return String.format("%s | %s | %s | %s", "E", super.saveString(), Parser.formatDateTimeForSave(this.from), Parser.formatDateTimeForSave(this.to));
    }
}
