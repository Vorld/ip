package chuck.task;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import chuck.command.Parser;


/**
 * Represents an event task with start and end times.
 * Extends the basic Task functionality with from and to date/time fields.
 */
public class Event extends Task {
    public static final String TYPE_SYMBOL = "E";
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

        assert from != null && to != null : "Event start and end times cannot be null";
        assert !from.isAfter(to) : "Event start time cannot be after end time: " + from + " > " + to;

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

        assert from != null && to != null : "Event start and end times cannot be null";
        assert !from.isAfter(to) : "Event start time cannot be after end time: " + from + " > " + to;

        this.from = from;
        this.to = to;
    }

    /**
     * Creates a new event with description, completion status, times, and tags.
     *
     * @param description the description of the event
     * @param isDone whether the event is completed
     * @param from the start date and time of the event
     * @param to the end date and time of the event
     * @param tags the set of tags for this event
     */
    public Event(String description, boolean isDone, LocalDateTime from, LocalDateTime to, Set<String> tags) {
        super(description, isDone, tags);

        assert from != null && to != null : "Event start and end times cannot be null";
        assert !from.isAfter(to) : "Event start time cannot be after end time: " + from + " > " + to;

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
        return String.format("[%s]%s (from: %s to: %s)", TYPE_SYMBOL, super.toString(),
                Parser.formatDateTime(this.from),
                Parser.formatDateTime(this.to));
    }

    /**
     * Returns formatted string for file storage with event data.
     *
     * @return string representation suitable for saving to file with time range
     */
    @Override
    public String toSaveString() {
        return String.format("%s | %s | %s | %s", "E", super.toSaveString(), Parser.formatDateTimeForSave(this.from),
                Parser.formatDateTimeForSave(this.to));
    }

    /**
     * Creates an Event from a saved string line.
     *
     * @param line the saved string line in format "E | isDone | description | tags | startDate | endDate"
     * @return Event instance parsed from the save string
     */
    public static Event fromSaveString(String line) {
        String[] data = line.split("\\|");
        boolean isDone = Boolean.parseBoolean(data[1].trim());
        String description = data[2].trim();
        String tagString = data[3].trim();
        String startDate = data[4].trim();
        String endDate = data[5].trim();
        Set<String> tags = tagString.isEmpty() ? new HashSet<>() :
            new HashSet<>(Arrays.asList(tagString.split(",")));

        LocalDateTime fromDateTime = Parser.parseDateTime(startDate);
        LocalDateTime toDateTime = Parser.parseDateTime(endDate);
        return new Event(description, isDone, fromDateTime, toDateTime, tags);
    }
}
