package chuck.command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chuck.ChuckException;

public class ParserTest {

    @Test
    public void parse_markCommand_returnsMarkCommand() throws ChuckException {
        Command result = Parser.parse("mark 3");
        assertTrue(result instanceof MarkCommand);
    }

    @Test
    public void parse_markCommandWithExtraSpaces_returnsMarkCommand() throws ChuckException {
        Command result = Parser.parse("mark   5  ");
        assertTrue(result instanceof MarkCommand);
    }

    @Test
    public void parse_unmarkCommand_returnsUnmarkCommand() throws ChuckException {
        Command result = Parser.parse("unmark 2");
        assertTrue(result instanceof UnmarkCommand);
    }

    @Test
    public void parse_unmarkCommandWithExtraSpaces_returnsUnmarkCommand() throws ChuckException {
        Command result = Parser.parse("unmark   7   ");
        assertTrue(result instanceof UnmarkCommand);
    }

    @Test
    public void parse_deleteCommand_returnsDeleteCommand() throws ChuckException {
        Command result = Parser.parse("delete 1");
        assertTrue(result instanceof DeleteCommand);
    }

    @Test
    public void parse_deleteCommandWithExtraSpaces_returnsDeleteCommand() throws ChuckException {
        Command result = Parser.parse("delete    4   ");
        assertTrue(result instanceof DeleteCommand);
    }

    @Test
    public void parse_todoCommand_returnsTodoCommand() throws ChuckException {
        Command result = Parser.parse("todo read book");
        assertTrue(result instanceof TodoCommand);
    }

    @Test
    public void parse_todoCommandWithExtraSpaces_returnsTodoCommand() throws ChuckException {
        Command result = Parser.parse("todo   write report   ");
        assertTrue(result instanceof TodoCommand);
    }

    @Test
    public void parse_todoCommandEmpty_throwsChuckException() throws ChuckException {
        assertThrows(ChuckException.class, () -> Parser.parse("todo"));
    }

    @Test
    public void parse_deadlineCommand_returnsDeadlineCommand() throws ChuckException {
        Command result = Parser.parse("deadline submit CS2103 IP /by 2025-12-01 23:59");
        assertTrue(result instanceof DeadlineCommand);
    }

    @Test
    public void parse_deadlineCommandWithExtraSpaces_returnsDeadlineCommand() throws ChuckException {
        Command result = Parser.parse("deadline   finish homework   /by   2025-12-15 10:00  ");
        assertTrue(result instanceof DeadlineCommand);
    }

    @Test
    public void parse_eventCommand_returnsEventCommand() throws ChuckException {
        Command result = Parser.parse("event team meeting /from 2025-12-01 14:00 /to 2025-12-01 16:00");
        assertTrue(result instanceof EventCommand);
    }

    @Test
    public void parse_eventCommandWithExtraSpaces_returnsEventCommand() throws ChuckException {
        Command result = Parser.parse("event   project meeting   /from   2025-12-10 09:00   /to   2025-12-10 11:00  ");
        assertTrue(result instanceof EventCommand);
    }

    @Test
    public void parse_listCommand_returnsListCommand() throws ChuckException {
        Command result = Parser.parse("list");
        assertTrue(result instanceof ListCommand);
    }

    @Test
    public void parse_findCommand_returnsFindCommand() throws ChuckException {
        Command result = Parser.parse("find book");
        assertTrue(result instanceof FindCommand);
    }

    @Test
    public void parse_saveCommand_returnsSaveCommand() throws ChuckException {
        Command result = Parser.parse("save");
        assertTrue(result instanceof SaveCommand);
    }

    @Test
    public void parse_tagCommand_returnsTagCommand() throws ChuckException {
        Command result = Parser.parse("tag 1 work,urgent");
        assertTrue(result instanceof TagCommand);
    }

    @Test
    public void parse_tagCommandWithExtraSpaces_returnsTagCommand() throws ChuckException {
        Command result = Parser.parse("tag   2   personal,home   ");
        assertTrue(result instanceof TagCommand);
    }

    @Test
    public void parse_tagCommandRemove_returnsTagCommand() throws ChuckException {
        Command result = Parser.parse("tag 3 -work");
        assertTrue(result instanceof TagCommand);
    }

    @Test
    public void parse_filterCommand_returnsFilterCommand() throws ChuckException {
        Command result = Parser.parse("filter work");
        assertTrue(result instanceof FilterCommand);
    }

    @Test
    public void parse_filterCommandWithExtraSpaces_returnsFilterCommand() throws ChuckException {
        Command result = Parser.parse("filter   urgent   ");
        assertTrue(result instanceof FilterCommand);
    }

    @Test
    public void parse_byeCommand_returnsByeCommand() throws ChuckException {
        Command result = Parser.parse("bye");
        assertTrue(result instanceof ByeCommand);
        assertTrue(result.isExit());
    }

    @Test
    public void parse_invalidCommand_throwsChuckException() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("invalid");
        });
    }

    @Test
    public void parse_invalidMarkCommand_throwsChuckException() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("mark abc");
        });
    }

    @Test
    public void parse_invalidDeleteCommand_throwsChuckException() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("delete xyz");
        });
    }

    @Test
    public void parse_invalidUnmarkCommand_throwsChuckException() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("unmark test");
        });
    }

    @Test
    public void parse_deadlineCommandMissingBy_throwsChuckException() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("deadline submit report tomorrow");
        });
    }

    @Test
    public void parse_eventCommandMissingFrom_throwsChuckException() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("event meeting /to 2025-12-01 16:00");
        });
    }

    @Test
    public void parse_eventCommandMissingTo_throwsChuckException() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("event meeting /from 2025-12-01 14:00");
        });
    }

    @Test
    public void parse_tagCommandEmpty_throwsChuckException() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("tag");
        });
    }

    @Test
    public void parse_tagCommandMissingTags_throwsChuckException() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("tag 1");
        });
    }

    @Test
    public void parse_tagCommandInvalidTaskNumber_throwsChuckException() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("tag abc work");
        });
    }

    @Test
    public void parse_filterCommandEmpty_throwsChuckException() {
        assertThrows(ChuckException.class, () -> {
            Parser.parse("filter");
        });
    }

    @Test
    public void isExit_onlyByeCommand_returnsTrue() throws ChuckException {
        assertTrue(Parser.parse("bye").isExit());
        assertFalse(Parser.parse("list").isExit());
        assertFalse(Parser.parse("todo test").isExit());
        assertFalse(Parser.parse("save").isExit());
    }
}
