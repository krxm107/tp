package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MembershipClubCommand;
import seedu.address.logic.search.predicates.MembershipStatusPredicate;

public class MembershipClubCommandParserTest {

    private ListMemberCommandParser parser = new ListMemberCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        // No keywords
        assertParseSuccess(parser, "1  ",
                new MembershipClubCommand(INDEX_FIRST_CLUB, new MembershipStatusPredicate()));

        // Empty keywords
        assertParseSuccess(parser, "1 /  ",
                new MembershipClubCommand(INDEX_FIRST_CLUB, new MembershipStatusPredicate()));

        // Valid keywords
        MembershipStatusPredicate predicate = new MembershipStatusPredicate();
        predicate.addPredicate("a");
        predicate.addPredicate("c");
        assertParseSuccess(parser, "1 /ac", new MembershipClubCommand(INDEX_FIRST_CLUB, predicate));

        // Valid keywords with random letters
        assertParseSuccess(parser, "1 / abcd  ", new MembershipClubCommand(INDEX_FIRST_CLUB, predicate));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing index
        assertParseFailure(parser, "/ac", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MembershipClubCommand.MESSAGE_USAGE));

        // Invalid index
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MembershipClubCommand.MESSAGE_USAGE));
    }
}
