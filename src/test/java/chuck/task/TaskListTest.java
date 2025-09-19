package chuck.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskListTest {
    private TaskList taskList;
    private TaskStub task1;
    private TaskStub task2;
    private TaskStub task3;

    // TaskStub for testing purposes
    private static class TaskStub extends Task {
        public TaskStub(String description) {
            super(description);
        }

        public TaskStub(String description, boolean isDone) {
            super(description, isDone);
        }

        @Override
        public String toSaveString() {
            return "STUB | " + super.toSaveString();
        }
    }

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        task1 = new TaskStub("read book about programming");
        task2 = new TaskStub("write report for CS2103");
        task3 = new TaskStub("attend meeting tomorrow");

        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
    }

    @Test
    public void find_partialMatch_returnsMatchingTasks() {
        TaskList result = taskList.find("book");
        assertEquals(1, result.size());
        assertEquals(task1, result.get(1));
    }

    @Test
    public void find_fullMatch_returnsMatchingTasks() {
        TaskList result = taskList.find("read book about programming");
        assertEquals(1, result.size());
        assertEquals(task1, result.get(1));
    }

    @Test
    public void find_multipleMatches_returnsAllMatchingTasks() {
        TaskList result = taskList.find("re"); // matches "read" and "report"
        assertEquals(2, result.size());
    }

    @Test
    public void find_noMatch_returnsEmptyTaskList() {
        TaskList result = taskList.find("nonexistent");
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }

    @Test
    public void find_caseSensitiveSearch_matchesExactCase() {
        TaskList result = taskList.find("BOOK");
        assertEquals(0, result.size()); // should not match "book"
    }

    @Test
    public void find_emptyString_returnsAll() {
        TaskList result = taskList.find("");
        assertEquals(3, result.size());
    }

    @Test
    public void find_searchInCompletedTask_findsTask() {
        task1.markDone();
        TaskList result = taskList.find("book");
        assertEquals(1, result.size());
        assertEquals(task1, result.get(1));
    }

    @Test
    public void find_nullSearchString_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> {
            taskList.find(null);
        });
    }
}
