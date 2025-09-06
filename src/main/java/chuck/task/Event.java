package chuck.task;

import java.time.LocalDateTime;

import chuck.command.Parser;


/**
 * Represents an event task with start and end times.
 * Extends the basic Task functionality with from and to date/time fields.
 */
public class Event extends Task {
    public static final String SHORT_HAND = "E";
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Creates a new event with description, start and end times.
     *
     * @param description the description of the event
     * @param from the start date and time of the event
     * @param to the end date and time of the event
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates a new event with description, completion status, and times.
     *
     * @param description the description of the event
     * @param isDone whether the event is completed
     * @param from the start date and time of the event
     * @param to the end date and time of the event
     */
    public Event(String description, boolean isDone, LocalDateTime from, LocalDateTime to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }


    /**
     * Returns formatted string with [E] prefix and event time range.
     *
     * @return string representation with event type indicator and time range
     */
    @Override
    public String toString() {
        return String.format("[%s]%s (from: %s to: %s)", SHORT_HAND, super.toString(), Parser.formatDateTime(this.from),
                Parser.formatDateTime(this.to));
    }

    /**
     * Returns formatted string for file storage with event data.
     *
     * @return string representation suitable for saving to file with time range
     */
    @Override
    public String saveString() {
        return String.format("%s | %s | %s | %s", "E", super.saveString(), Parser.formatDateTimeForSave(this.from),
                Parser.formatDateTimeForSave(this.to));
    }
}
