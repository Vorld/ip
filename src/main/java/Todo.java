public class Todo extends Task{
    private static final String SHORT_HAND = "T";

    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String toString() {
        return String.format("[%s]%s", SHORT_HAND, super.toString());
    }

    @Override
    public String saveString() {
        return String.format("%s | %s", "T", super.saveString());
    }
}
