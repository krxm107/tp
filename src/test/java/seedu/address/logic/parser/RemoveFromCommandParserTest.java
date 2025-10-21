package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLUB_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEMBER_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLUB_INDEX_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLUB_INDEX_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLUB_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEMBER_INDEX_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEMBER_INDEX_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEMBER_INDEX_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveFromCommand;

public class RemoveFromCommandParserTest {

    private final RemoveFromCommandParser parser = new RemoveFromCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index[] expectedMemberIndexes = new Index[] { Index.fromOneBased(VALID_MEMBER_INDEX_1),
                Index.fromOneBased(VALID_MEMBER_INDEX_2) };
        Index[] expectedClubIndexes = new Index[] { Index.fromOneBased(VALID_CLUB_INDEX_1),
                Index.fromOneBased(VALID_CLUB_INDEX_2) };

        assertParseSuccess(parser,
                VALID_MEMBER_INDEX_DESC + VALID_CLUB_INDEX_DESC,
                new RemoveFromCommand(expectedMemberIndexes, expectedClubIndexes));
    }

    @Test
    public void parse_missingCompulsoryPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveFromCommand.MESSAGE_USAGE);

        // missing member prefix
        assertParseFailure(parser, VALID_CLUB_INDEX_DESC, expectedMessage);

        // missing club prefix
        assertParseFailure(parser, VALID_MEMBER_INDEX_DESC, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveFromCommand.MESSAGE_USAGE);

        // invalid member index
        assertParseFailure(parser, INVALID_MEMBER_INDEX_DESC + VALID_CLUB_INDEX_DESC, expectedMessage);

        // invalid club index
        assertParseFailure(parser, VALID_MEMBER_INDEX_DESC + INVALID_CLUB_INDEX_DESC, expectedMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveFromCommand.MESSAGE_USAGE);

        assertParseFailure(parser,
                "randomtext " + VALID_MEMBER_INDEX_DESC + VALID_CLUB_INDEX_DESC,
                expectedMessage);
    }
}
