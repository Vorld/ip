package chuck.command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chuck.ChuckException;

public class ParserTest {

    @Test
    public void testParse_MarkCommand() throws ChuckException {
        Command result = Parser.parse("mark 3");
        assertTrue(result instanceof MarkCommand);
    }

    @Test
    public void testParse_MarkCommandWithExtraSpaces() throws ChuckException {
        Command result = Parser.parse("mark   5  ");
        assertTrue(result instanceof MarkCommand);
    }

    @Test
    public void testParse_UnmarkCommand() throws ChuckException {
        Command result = Parser.parse("unmark 2");
        assertTrue(result instanceof UnmarkCommand);
    }

    @Test
    public void testParse_UnmarkCommandWithExtraSpaces() throws ChuckException {
        Command result = Parser.parse("unmark   7   ");
        assertTrue(result instanceof UnmarkCommand);
    }

    @Test
    public void testParse_DeleteCommand() throws ChuckException {
        Command result = Parser.parse("delete 1");
        assertTrue(result instanceof DeleteCommand);
    }

    @Test
    public void testParse_DeleteCommandWithExtraSpaces() throws ChuckException {
        Command result = Parser.parse("delete    4   ");
        assertTrue(result instanceof DeleteCommand);
    }

    @Test
    public void testParse_TodoCommand() throws ChuckException {
        Command result = Parser.parse("todo read book");
        assertTrue(result instanceof TodoCommand);
    }

    @Test
    public void testParse_TodoCommandWithExtraSpaces() throws ChuckException {
        Command result = Parser.parse("todo   write report   ");
        assertTrue(result instanceof TodoCommand);
    }

    @Test
    public void testParse_TodoCommandEmpty() throws ChuckException {
        Command result = Parser.parse("todo");
        assertTrue(result instanceof TodoCommand);
    }

    @Test
    public void testParse_DeadlineCommand() throws ChuckException {
        Command result = Parser.parse("deadline submit CS2103 IP /by 2025-12-01 23:59");
        assertTrue(result instanceof DeadlineCommand);
    }

    @Test
    public void testParse_DeadlineCommandWithExtraSpaces() throws ChuckException {
        Command result = Parser.parse("deadline   finish homework   /by   2025-12-15 10:00  ");
        assertTrue(result instanceof DeadlineCommand);
    }

    @Test
    public void testParse_EventCommand() throws ChuckException {
        Command result = Parser.parse("event team meeting /from 2025-12-01 14:00 /to 2025-12-01 16:00");
        assertTrue(result instanceof EventCommand);
    }

    @Test
    public void testParse_EventCommandWithExtraSpaces() throws ChuckException {
        Command result = Parser.parse("event   project meeting   /from   2025-12-10 09:00   /to   2025-12-10 11:00  ");
        assertTrue(result instanceof EventCommand);
    }

    @Test
    public void testParse_ListCommand() throws ChuckException {
        Command result = Parser.parse("list");
        assertTrue(result instanceof ListCommand);
    }

    @Test
    public void testParse_FindCommand() throws ChuckException {
        Command result = Parser.parse("find book");
        assertTrue(result instanceof FindCommand);
    }

    @Test
    public void testParse_SaveCommand() throws ChuckException {
        Command result = Parser.parse("save");
        assertTrue(result instanceof SaveCommand);
    }

    @Test
    public void testParse_TagCommand() throws ChuckException {
        Command result = Parser.parse("tag 1 work,urgent");
        assertTrue(result instanceof TagCommand);
    }

    @Test
    public void testParse_TagCommandWithExtraSpaces() throws ChuckException {
        Command result = Parser.parse("tag   2   personal,home   ");
        assertTrue(result instanceof TagCommand);
    }

    @Test
    public void testParse_TagCommandRemove() throws ChuckException {
        Command result = Parser.parse("tag 3 -work");
        assertTrue(result instanceof TagCommand);
    }

    @Test
    public void testParse_FilterCommand() throws ChuckException {
        Command result = Parser.parse("filter work");
        assertTrue(result instanceof FilterCommand);
    }

    @Test
    public void testParse_FilterCommandWithExtraSpaces() throws ChuckException {
        Command result = Parser.parse("filter   urgent   ");
        assertTrue(result instanceof FilterCommand);
    }

    @Test
    public void testParse_ByeCommand() throws ChuckException {
        Command result = Parser.parse("bye");
        assertTrue(result instanceof ByeCommand);
        assertTrue(result.isExit());
    }

    @Test
    public void testParse_InvalidCommand() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("invalid");
        });
    }

    @Test
    public void testParse_InvalidMarkCommand() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("mark abc");
        });
    }

    @Test
    public void testParse_InvalidDeleteCommand() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("delete xyz");
        });
    }

    @Test
    public void testParse_InvalidUnmarkCommand() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("unmark test");
        });
    }

    @Test
    public void testParse_DeadlineCommandMissingBy() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("deadline submit report tomorrow");
        });
    }

    @Test
    public void testParse_EventCommandMissingFrom() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("event meeting /to 2025-12-01 16:00");
        });
    }

    @Test
    public void testParse_EventCommandMissingTo() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("event meeting /from 2025-12-01 14:00");
        });
    }

    @Test
    public void testParse_TagCommandEmpty() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("tag");
        });
    }

    @Test
    public void testParse_TagCommandMissingTags() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("tag 1");
        });
    }

    @Test
    public void testParse_TagCommandInvalidTaskNumber() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("tag abc work");
        });
    }

    @Test
    public void testParse_FilterCommandEmpty() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("filter");
        });
    }

    @Test
    public void testIsExit_OnlyByeCommandReturnsTrue() throws ChuckException {
        assertTrue(Parser.parse("bye").isExit());
        assertFalse(Parser.parse("list").isExit());
        assertFalse(Parser.parse("todo test").isExit());
        assertFalse(Parser.parse("save").isExit());
    }
}
