package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.ListMembershipCommand;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ListMembershipCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the ListMembershipCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ListMembershipCommandParserTest {

    private ListMembershipCommandParser parser = new ListMembershipCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        assertParseSuccess(parser, "1", new ListMembershipCommand(INDEX_FIRST_CLUB));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListMembershipCommand.MESSAGE_USAGE));
    }
}
