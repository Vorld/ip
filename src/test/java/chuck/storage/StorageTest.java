package chuck.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chuck.ChuckException;
import chuck.task.Deadline;
import chuck.task.Event;
import chuck.task.Task;
import chuck.task.TaskList;
import chuck.task.Todo;

public class StorageTest {
    
    private Storage storage;
    private File dataDir;
    private File saveFile;

    @BeforeEach
    public void setUp() throws IOException {
        storage = new Storage("./data/chuck.txt");
        dataDir = new File("./data");
        saveFile = new File("./data/chuck.txt");

        if (saveFile.exists()) {
            saveFile.delete();
        }
        if (dataDir.exists()) {
            dataDir.delete();
        }
    }

    @AfterEach
    public void tearDown() {
        if (saveFile.exists()) {
            saveFile.delete();
        }
        if (dataDir.exists()) {
            dataDir.delete();
        }
    }

    private void writeToFile(String content) throws IOException {
        dataDir.mkdirs();
        try (FileWriter writer = new FileWriter(saveFile)) {
            writer.write(content);
        }
    }


    @Test
    public void loadTasks_singleTodoTask_returnsTaskList() throws ChuckException, IOException {
        writeToFile("T | false | read book | ");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Todo);
        assertEquals(" ", task.getStatusIcon());
        assertTrue(task.toString().contains("read book"));
    }

    @Test
    public void loadTasks_singleCompletedTodoTask_returnsTaskListWithDoneTask() throws ChuckException, IOException {
        writeToFile("T | true | complete assignment | ");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Todo);
        assertEquals("X", task.getStatusIcon());
        assertTrue(task.toString().contains("complete assignment"));
    }

    @Test
    public void loadTasks_singleDeadlineTask_returnsTaskList() throws ChuckException, IOException {
        writeToFile("D | false | submit report | | 2023-12-01 23:59");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Deadline);
        assertTrue(task.toString().contains("submit report"));
        assertTrue(task.toString().contains("by:"));
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void loadTasks_singleCompletedDeadlineTask_returnsTaskListWithDoneTask() throws ChuckException, IOException {
        writeToFile("D | true | finish homework | | 2023-12-15 10:00");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Deadline);
        assertTrue(task.toString().contains("finish homework"));
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void loadTasks_singleEventTask_returnsTaskList() throws ChuckException, IOException {
        writeToFile("E | false | team meeting | | 2023-12-01 14:00 | 2023-12-01 16:00");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Event);
        assertTrue(task.toString().contains("team meeting"));
        assertTrue(task.toString().contains("from:"));
        assertTrue(task.toString().contains("to:"));
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void loadTasks_singleCompletedEventTask_returnsTaskListWithDoneTask() throws ChuckException, IOException {
        writeToFile("E | true | project presentation | | 2023-12-10 09:00 | 2023-12-10 11:00");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Event);
        assertTrue(task.toString().contains("project presentation"));
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void loadTasks_multipleMixedTasks_returnsCompleteTaskList() throws ChuckException, IOException {
        String content = "T | false | read book | \n"
                + "D | true | submit report | | 2023-12-01 23:59\n"
                + "E | false | team meeting | | 2023-12-01 14:00 | 2023-12-01 16:00";
        writeToFile(content);
        
        TaskList result = storage.loadTasks();
        
        assertEquals(3, result.size());

        Task todoTask = result.get(1);
        assertTrue(todoTask instanceof Todo);
        assertEquals(" ", todoTask.getStatusIcon());

        Task deadlineTask = result.get(2);
        assertTrue(deadlineTask instanceof Deadline);
        assertEquals("X", deadlineTask.getStatusIcon());

        Task eventTask = result.get(3);
        assertTrue(eventTask instanceof Event);
        assertEquals(" ", eventTask.getStatusIcon());
    }


    @Test
    public void loadTasks_tasksWithExtraWhitespace_returnsTrimedTasks() throws ChuckException, IOException {
        String content = "T |  false  |  read book with spaces |  \n"
                + "D | true   |   submit report   | |  2023-12-01 23:59 \n"
                + "E |false|meeting| |  2023-12-01 14:00| 2023-12-01 16:00";
        writeToFile(content);
        
        TaskList result = storage.loadTasks();
        
        assertEquals(3, result.size());
        assertTrue(result.get(1).toString().contains("read book with spaces"));
        assertTrue(result.get(2).toString().contains("submit report"));
        assertTrue(result.get(3).toString().contains("meeting"));
    }

    @Test
    public void loadTasks_corruptedDateFormat_throwsChuckException() throws IOException {
        writeToFile("D | false | submit report | | invalid-date-format");

        assertThrows(ChuckException.class, () -> storage.loadTasks());
    }

    @Test
    public void loadTasks_corruptedBooleanFormat_treatsFalseAsDefault() throws ChuckException, IOException {
        writeToFile("T | maybe | read book| ");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        assertEquals(" ", result.get(1).getStatusIcon());
    }

    @Test
    public void loadTasks_unknownTaskType_throwsChuckException() throws ChuckException, IOException {
        writeToFile("X | false | unknown task type | ");

        assertThrows(ChuckException.class, () -> storage.loadTasks());
    }

    @Test
    public void loadTasks_malformedLine_throwsChuckException() throws ChuckException, IOException {
        String content = "T | false | valid task | \n"
                + "malformed line without pipes | \n"
                + "D | true | another valid task | | 2023-12-01 23:59";
        writeToFile(content);

        assertThrows(ChuckException.class, () -> storage.loadTasks());
        // not sure how to check the returned tasks with JUnit
    }

    @Test
    public void loadTasks_todoWithTags_returnsTaskWithTags() throws ChuckException, IOException {
        writeToFile("T | false | read book | work,personal");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Todo);
        assertTrue(task.hasTag("work"));
        assertTrue(task.hasTag("personal"));
    }

    @Test
    public void loadTasks_deadlineWithTags_returnsTaskWithTags() throws ChuckException, IOException {
        writeToFile("D | true | submit report | urgent,work | 2023-12-01 23:59");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Deadline);
        assertTrue(task.hasTag("urgent"));
        assertTrue(task.hasTag("work"));
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void loadTasks_eventWithTags_returnsTaskWithTags() throws ChuckException, IOException {
        writeToFile("E | false | team meeting | work,meeting | 2023-12-01 14:00 | 2023-12-01 16:00");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Event);
        assertTrue(task.hasTag("work"));
        assertTrue(task.hasTag("meeting"));
    }

    @Test
    public void loadTasks_mixedTasksWithTags_returnsAllTasksWithTags() throws ChuckException, IOException {
        String content = "T | false | read book | personal\n"
                + "D | true | submit report | work,urgent | 2023-12-01 23:59\n"
                + "E | false | team meeting | work | 2023-12-01 14:00 | 2023-12-01 16:00";
        writeToFile(content);
        
        TaskList result = storage.loadTasks();
        
        assertEquals(3, result.size());

        Task todoTask = result.get(1);
        assertTrue(todoTask.hasTag("personal"));
        
        Task deadlineTask = result.get(2);
        assertTrue(deadlineTask.hasTag("work"));
        assertTrue(deadlineTask.hasTag("urgent"));

        Task eventTask = result.get(3);
        assertTrue(eventTask.hasTag("work"));
    }
}
