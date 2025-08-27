package chuck.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testParseArguments_MarkCommand() {
        String[] result = Parser.parseArguments("mark 3", Command.MARK);
        assertEquals(1, result.length);
        assertEquals("3", result[0]);
    }

    @Test
    public void testParseArguments_MarkCommandWithExtraSpaces() {
        String[] result = Parser.parseArguments("mark   5  ", Command.MARK);
        assertEquals(1, result.length);
        assertEquals("5", result[0]);
    }

    @Test
    public void testParseArguments_UnmarkCommand() {
        String[] result = Parser.parseArguments("unmark 2", Command.UNMARK);
        assertEquals(1, result.length);
        assertEquals("2", result[0]);
    }

    @Test
    public void testParseArguments_UnmarkCommandWithExtraSpaces() {
        String[] result = Parser.parseArguments("unmark   7   ", Command.UNMARK);
        assertEquals(1, result.length);
        assertEquals("7", result[0]);
    }

    @Test
    public void testParseArguments_DeleteCommand() {
        String[] result = Parser.parseArguments("delete 1", Command.DELETE);
        assertEquals(1, result.length);
        assertEquals("1", result[0]);
    }

    @Test
    public void testParseArguments_DeleteCommandWithExtraSpaces() {
        String[] result = Parser.parseArguments("delete    4   ", Command.DELETE);
        assertEquals(1, result.length);
        assertEquals("4", result[0]);
    }

    @Test
    public void testParseArguments_TodoCommand() {
        String[] result = Parser.parseArguments("todo read book", Command.TODO);
        assertEquals(1, result.length);
        assertEquals("read book", result[0]);
    }

    @Test
    public void testParseArguments_TodoCommandWithExtraSpaces() {
        String[] result = Parser.parseArguments("todo   write report   ", Command.TODO);
        assertEquals(1, result.length);
        assertEquals("write report", result[0]);
    }

    @Test
    public void testParseArguments_TodoCommandEmpty() {
        String[] result = Parser.parseArguments("todo", Command.TODO);
        assertEquals(1, result.length);
        assertEquals("", result[0]);
    }

    @Test
    public void testParseArguments_DeadlineCommand() {
        String[] result = Parser.parseArguments("deadline submit CS2103 IP /by 2025-12-01 23:59", Command.DEADLINE);
        assertEquals(2, result.length);
        assertEquals("submit CS2103 IP", result[0]);
        assertEquals("2025-12-01 23:59", result[1]);
    }

    @Test
    public void testParseArguments_DeadlineCommandWithExtraSpaces() {
        String[] result = Parser.parseArguments("deadline   finish homework   /by   2025-12-15 10:00  ", Command.DEADLINE);
        assertEquals(2, result.length);
        assertEquals("finish homework", result[0]);
        assertEquals("2025-12-15 10:00", result[1]);
    }

    @Test
    public void testParseArguments_EventCommand() {
        String[] result = Parser.parseArguments("event team meeting /from 2025-12-01 14:00 /to 2025-12-01 16:00", Command.EVENT);
        assertEquals(3, result.length);
        assertEquals("team meeting", result[0]);
        assertEquals("2025-12-01 14:00", result[1]);
        assertEquals("2025-12-01 16:00", result[2]);
    }

    @Test
    public void testParseArguments_EventCommandWithExtraSpaces() {
        String[] result = Parser.parseArguments("event   project meeting   /from   2025-12-10 09:00   /to   2025-12-10 11:00  ", Command.EVENT);
        assertEquals(3, result.length);
        assertEquals("project meeting", result[0]);
        assertEquals("2025-12-10 09:00", result[1]);
        assertEquals("2025-12-10 11:00", result[2]);
    }

    @Test
    public void testParseArguments_ListCommand() {
        String[] result = Parser.parseArguments("list", Command.LIST);
        assertEquals(0, result.length);
    }

    @Test
    public void testParseArguments_SaveCommand() {
        String[] result = Parser.parseArguments("save", Command.SAVE);
        assertEquals(0, result.length);
    }

    @Test
    public void testParseArguments_ByeCommand() {
        String[] result = Parser.parseArguments("bye", Command.BYE);
        assertEquals(0, result.length);
    }

    @Test
    public void testParseArguments_EdgeCaseMinimalInput() {
        String[] result = Parser.parseArguments("todo a", Command.TODO);
        assertEquals(1, result.length);
        assertEquals("a", result[0]);
    }

    @Test
    public void testParseArguments_EdgeCaseDeadlineMinimal() {
        String[] result = Parser.parseArguments("deadline a /by b", Command.DEADLINE);
        assertEquals(2, result.length);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
    }

    @Test
    public void testParseArguments_EdgeCaseEventMinimal() {
        String[] result = Parser.parseArguments("event a /from b /to c", Command.EVENT);
        assertEquals(3, result.length);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
        assertEquals("c", result[2]);
    }
}