package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.membership.MembershipStatus.ACTIVE;
import static seedu.address.model.membership.MembershipStatus.CANCELLED;
import static seedu.address.model.membership.MembershipStatus.EXPIRED;
import static seedu.address.model.membership.MembershipStatus.PENDING_CANCELLATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLUB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MembershipClubCommand;
import seedu.address.logic.search.predicates.MembershipStatusPredicate;

import java.util.Arrays;

public class MembershipClubCommandParserTest {

    private MembershipClubCommandParser parser = new MembershipClubCommandParser();
    private MembershipStatusPredicate defaultPredicate =
            new MembershipStatusPredicate(Arrays.asList(ACTIVE, EXPIRED, PENDING_CANCELLATION));
    private MembershipStatusPredicate testPredicate =
            new MembershipStatusPredicate(Arrays.asList(ACTIVE, CANCELLED));

    @Test
    public void parse_validArgs_returnsListCommand() {
        // No keywords
        assertParseSuccess(parser, "1  ", new MembershipClubCommand(INDEX_FIRST_CLUB, defaultPredicate));

        // Valid keywords
        assertParseSuccess(parser, "1 a c", new MembershipClubCommand(INDEX_FIRST_CLUB, testPredicate));

        // Valid keywords with random letters
        assertParseSuccess(parser, "1   abcd  ", new MembershipClubCommand(INDEX_FIRST_CLUB, testPredicate));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing index
        assertParseFailure(parser, "ac 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MembershipClubCommand.MESSAGE_USAGE));

        // Invalid index
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MembershipClubCommand.MESSAGE_USAGE));
    }
}
