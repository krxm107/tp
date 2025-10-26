package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListMembershipCommand;

public class ListMembershipCommandParserTest {

    private ListMembershipCommandParser parser = new ListMembershipCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        // No keywords
        assertParseSuccess(parser, "1  ",
                new ListMembershipCommand(INDEX_FIRST_PERSON, new MembershipStatusPredicate()));

        // Empty keywords
        assertParseSuccess(parser, "1 /  ",
                new ListMembershipCommand(INDEX_FIRST_PERSON, new MembershipStatusPredicate()));

        // Valid keywords
        MembershipStatusPredicate predicate = new MembershipStatusPredicate();
        predicate.addPredicate("e");
        predicate.addPredicate("p");
        assertParseSuccess(parser, "1 /ep", new ListMembershipCommand(INDEX_FIRST_PERSON, predicate));

        // Valid keywords with random letters
        assertParseSuccess(parser, "1 / efgp  ", new ListMembershipCommand(INDEX_FIRST_PERSON, predicate));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing index
        assertParseFailure(parser, "/ep", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListMembershipCommand.MESSAGE_USAGE));

        // Invalid index
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListMembershipCommand.MESSAGE_USAGE));
    }
}
