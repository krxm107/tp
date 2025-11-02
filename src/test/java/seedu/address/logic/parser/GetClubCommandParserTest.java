package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.GetClubCommand;

public class GetClubCommandParserTest {

    private GetClubCommandParser parser = new GetClubCommandParser();

    @Test
    public void parse_validArgs_returnsGetClubCommand() {
        // no specifications
        GetClubCommand expectedGetCommand = new GetClubCommand(INDEX_FIRST_CLUB, Messages::format);
        assertParseSuccess(parser, "1   ", expectedGetCommand);

        // with specifications
        expectedGetCommand = new GetClubCommand(INDEX_FIRST_CLUB, club -> club.getName().fullName);
        assertParseSuccess(parser, "1   n", expectedGetCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // with extra characters
        assertParseFailure(parser, "1 n a e",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetClubCommand.MESSAGE_USAGE));

        // missing index
        assertParseFailure(parser, "aenpt 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetClubCommand.MESSAGE_USAGE));

        // invalid index
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetClubCommand.MESSAGE_USAGE));
    }
}
