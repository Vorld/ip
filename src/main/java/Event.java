public class Event extends Task{
    private static final String SHORT_HAND = "E";
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event(String description, boolean isDone, String from, String to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (from: %s to: %s)", SHORT_HAND, super.toString(), this.from, this.to);
    }

    @Override
    public String saveString() {
        return String.format("%s | %s | %s | %s", "E", super.saveString(), this.from, this.to);
    }
}
