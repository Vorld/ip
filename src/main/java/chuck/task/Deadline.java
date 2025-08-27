package chuck.task;

import chuck.command.Parser;
import java.time.LocalDateTime;

public class Deadline extends Task {
    public static final String SHORT_HAND = "D";
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
        return String.format("[%s]%s (by: %s)", SHORT_HAND, super.toString(), Parser.formatDateTime(this.by));
    }

    @Override
    public String saveString() {
        return String.format("%s | %s | %s", SHORT_HAND, super.saveString(), Parser.formatDateTimeForSave(this.by));
    }
}
