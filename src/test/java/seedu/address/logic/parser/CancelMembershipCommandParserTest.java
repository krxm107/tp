package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLUB_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLUB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CancelMembershipCommand;

public class CancelMembershipCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelMembershipCommand.MESSAGE_USAGE);

    private CancelMembershipCommandParser parser = new CancelMembershipCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetPersonIndex = INDEX_FIRST_PERSON;
        Index targetClubIndex = INDEX_FIRST_PERSON;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MEMBER_DESC_AMY + CLUB_DESC_AMY,
                new CancelMembershipCommand(targetPersonIndex, targetClubIndex));
    }

    @Test
    public void parse_repeatedPrefixes_failure() {
        // multiple members
        assertParseFailure(parser, MEMBER_DESC_BOB + MEMBER_DESC_AMY + CLUB_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEMBER));

        // multiple clubs
        assertParseFailure(parser, MEMBER_DESC_AMY + CLUB_DESC_AMY + CLUB_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLUB));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing member prefix
        assertParseFailure(parser, CLUB_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // missing club prefix
        assertParseFailure(parser, MEMBER_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // all prefixes missing
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid member index
        assertParseFailure(parser, INVALID_MEMBER_DESC + CLUB_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelMembershipCommand.MESSAGE_USAGE));

        // invalid club index
        assertParseFailure(parser, MEMBER_DESC_AMY + INVALID_CLUB_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelMembershipCommand.MESSAGE_USAGE));

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MEMBER_DESC + INVALID_CLUB_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelMembershipCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MEMBER_DESC_AMY + CLUB_DESC_AMY,
                MESSAGE_INVALID_FORMAT);
    }
}
