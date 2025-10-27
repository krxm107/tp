package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.membership.MembershipStatus.ACTIVE;
import static seedu.address.model.membership.MembershipStatus.EXPIRED;
import static seedu.address.model.membership.MembershipStatus.PENDING_CANCELLATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MembershipPersonCommand;
import seedu.address.logic.search.predicates.MembershipStatusPredicate;

import java.util.Arrays;

public class MembershipPersonCommandParserTest {

    private MembershipPersonCommandParser parser = new MembershipPersonCommandParser();
    private MembershipStatusPredicate defaultPredicate =
            new MembershipStatusPredicate(Arrays.asList(ACTIVE, EXPIRED, PENDING_CANCELLATION));
    private MembershipStatusPredicate testPredicate =
            new MembershipStatusPredicate(Arrays.asList(EXPIRED, PENDING_CANCELLATION));

    @Test
    public void parse_validArgs_returnsListCommand() {
        // No keywords
        assertParseSuccess(parser, "1  ",
                new MembershipPersonCommand(INDEX_FIRST_PERSON, defaultPredicate));

        // Valid keywords
        assertParseSuccess(parser, "1 e p", new MembershipPersonCommand(INDEX_FIRST_PERSON, testPredicate));

        // Valid keywords with random letters
        assertParseSuccess(parser, "1   efgp  ",
                new MembershipPersonCommand(INDEX_FIRST_PERSON, testPredicate));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing index
        assertParseFailure(parser, "ep 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MembershipPersonCommand.MESSAGE_USAGE));

        // Invalid index
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MembershipPersonCommand.MESSAGE_USAGE));
    }
}
