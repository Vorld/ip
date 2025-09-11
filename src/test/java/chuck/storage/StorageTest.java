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
        storage = new Storage();
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
    public void testLoadTasks_SingleTodoTask() throws ChuckException, IOException {
        writeToFile("T | false | read book");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Todo);
        assertEquals(" ", task.getStatusIcon());
        assertTrue(task.toString().contains("read book"));
    }

    @Test
    public void testLoadTasks_SingleTodoTaskDone() throws ChuckException, IOException {
        writeToFile("T | true | complete assignment");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Todo);
        assertEquals("X", task.getStatusIcon());
        assertTrue(task.toString().contains("complete assignment"));
    }

    @Test
    public void testLoadTasks_SingleDeadlineTask() throws ChuckException, IOException {
        writeToFile("D | false | submit report | 2023-12-01 23:59");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Deadline);
        assertTrue(task.toString().contains("submit report"));
        assertTrue(task.toString().contains("by:"));
        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void testLoadTasks_SingleDeadlineTaskDone() throws ChuckException, IOException {
        writeToFile("D | true | finish homework | 2023-12-15 10:00");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Deadline);
        assertTrue(task.toString().contains("finish homework"));
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void testLoadTasks_SingleEventTask() throws ChuckException, IOException {
        writeToFile("E | false | team meeting | 2023-12-01 14:00 | 2023-12-01 16:00");
        
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
    public void testLoadTasks_SingleEventTaskDone() throws ChuckException, IOException {
        writeToFile("E | true | project presentation | 2023-12-10 09:00 | 2023-12-10 11:00");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        Task task = result.get(1);
        assertTrue(task instanceof Event);
        assertTrue(task.toString().contains("project presentation"));
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void testLoadTasks_MultipleMixedTasks() throws ChuckException, IOException {
        String content = "T | false | read book\n"
                + "D | true | submit report | 2023-12-01 23:59\n"
                + "E | false | team meeting | 2023-12-01 14:00 | 2023-12-01 16:00";
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
    public void testLoadTasks_TasksWithExtraWhitespace() throws ChuckException, IOException {
        String content = "T |  false  |  read book with spaces  \n"
                + "D | true   |   submit report   | 2023-12-01 23:59 \n"
                + "E |false|meeting| 2023-12-01 14:00| 2023-12-01 16:00";
        writeToFile(content);
        
        TaskList result = storage.loadTasks();
        
        assertEquals(3, result.size());
        assertTrue(result.get(1).toString().contains("read book with spaces"));
        assertTrue(result.get(2).toString().contains("submit report"));
        assertTrue(result.get(3).toString().contains("meeting"));
    }

    @Test
    public void testLoadTasks_CorruptedDateFormat() throws IOException {
        writeToFile("D | false | submit report | invalid-date-format");

        assertThrows(ChuckException.class, () -> storage.loadTasks());
    }

    @Test
    public void testLoadTasks_CorruptedBooleanFormat() throws ChuckException, IOException {
        writeToFile("T | maybe | read book");
        
        TaskList result = storage.loadTasks();
        
        assertEquals(1, result.size());
        assertEquals(" ", result.get(1).getStatusIcon());
    }

    @Test
    public void testLoadTasks_UnknownTaskType() throws ChuckException, IOException {
        writeToFile("X | false | unknown task type");
        
        TaskList result = storage.loadTasks();

        assertEquals(0, result.size());
    }

    @Test
    public void testLoadTasks_MalformedLine() throws ChuckException, IOException {
        String content = "T | false | valid task\n"
                + "malformed line without pipes\n"
                + "D | true | another valid task | 2023-12-01 23:59";
        writeToFile(content);


        TaskList result = storage.loadTasks();
        
        assertEquals(2, result.size());
        assertTrue(result.get(1) instanceof Todo);
        assertTrue(result.get(2) instanceof Deadline);
    }
}
