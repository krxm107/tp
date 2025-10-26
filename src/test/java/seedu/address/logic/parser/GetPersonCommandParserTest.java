package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GetPersonCommand;

public class GetPersonCommandParserTest {

    private GetPersonCommandParser parser = new GetPersonCommandParser();

    @Test
    public void parse_validArgs_returnsGetPersonCommand() {
        // no specifications
        GetPersonCommand expectedGetCommand = new GetPersonCommand(INDEX_FIRST_CLUB, "");
        assertParseSuccess(parser, "1", expectedGetCommand);

        // empty specifications
        assertParseSuccess(parser, "1 /   ", expectedGetCommand);

        // with specifications
        expectedGetCommand = new GetPersonCommand(INDEX_FIRST_CLUB, "aenpt");
        assertParseSuccess(parser, "1 / a e n p t", expectedGetCommand);

        // with extra characters
        assertParseSuccess(parser, "1 /abcdenptuv123", expectedGetCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // missing index
        assertParseFailure(parser, "/aenpt1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetPersonCommand.MESSAGE_USAGE));

        // invalid index
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetPersonCommand.MESSAGE_USAGE));
    }
}
