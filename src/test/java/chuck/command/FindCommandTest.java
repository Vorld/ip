package chuck.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chuck.ChuckException;

public class FindCommandTest {

    @Test
    public void parse_validSearchString_returnsValidFindCommand() throws ChuckException {
        FindCommand result = FindCommand.parse("book");
        assertTrue(result instanceof FindCommand);
    }

    @Test
    public void parse_searchStringWithSpaces_trimsAndReturnsValidCommand() throws ChuckException {
        FindCommand result = FindCommand.parse("   read book   ");
        assertTrue(result instanceof FindCommand);
    }

    @Test
    public void parse_singleCharacterSearch_returnsValidCommand() throws ChuckException {
        FindCommand result = FindCommand.parse("a");
        assertTrue(result instanceof FindCommand);
    }

    @Test
    public void parse_longSearchString_returnsValidCommand() throws ChuckException {
        FindCommand result = FindCommand.parse("this is a very long search string with many words");
        assertTrue(result instanceof FindCommand);
    }

    @Test
    public void parse_searchStringWithSpecialCharacters_returnsValidCommand() throws ChuckException {
        FindCommand result = FindCommand.parse("test@email.com");
        assertTrue(result instanceof FindCommand);
    }

    @Test
    public void parse_searchStringWithNumbers_returnsValidCommand() throws ChuckException {
        FindCommand result = FindCommand.parse("CS2103T");
        assertTrue(result instanceof FindCommand);
    }

    @Test
    public void parse_emptyString_throwsChuckException() {
        ChuckException exception = assertThrows(ChuckException.class, () -> {
            FindCommand.parse("");
        });
        assertEquals("You blockhead! You can't search for nothing :(", exception.getMessage());
    }

    @Test
    public void parse_onlySpaces_throwsChuckException() {
        ChuckException exception = assertThrows(ChuckException.class, () -> {
            FindCommand.parse("   ");
        });
        assertEquals("You blockhead! You can't search for nothing :(", exception.getMessage());
    }
}
