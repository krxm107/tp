package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_ART;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_ART;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ART;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ART;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BIG;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CASUAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BIG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CASUAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalClubs.ART;
import static seedu.address.testutil.TypicalClubs.BOOKS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddClubCommand;
import seedu.address.model.club.Club;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ClubBuilder;

public class AddClubCommandParserTest {
    private AddClubCommandParser parser = new AddClubCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Club expectedClub = new ClubBuilder(BOOKS).withTags(VALID_TAG_CASUAL).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOOKS + PHONE_DESC_BOOKS + EMAIL_DESC_BOOKS
                + ADDRESS_DESC_BOOKS + TAG_DESC_CASUAL, new AddClubCommand(expectedClub));


        // multiple tags - all accepted
        Club expectedClubMultipleTags = new ClubBuilder(BOOKS).withTags(VALID_TAG_CASUAL, VALID_TAG_BIG)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOOKS + PHONE_DESC_BOOKS + EMAIL_DESC_BOOKS + ADDRESS_DESC_BOOKS
                        + TAG_DESC_BIG + TAG_DESC_CASUAL,
                new AddClubCommand(expectedClubMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedClubString = NAME_DESC_BOOKS + PHONE_DESC_BOOKS + EMAIL_DESC_BOOKS
                + ADDRESS_DESC_BOOKS + TAG_DESC_CASUAL;

        // multiple names
        assertParseFailure(parser, NAME_DESC_ART + validExpectedClubString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_ART + validExpectedClubString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_ART + validExpectedClubString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_ART + validExpectedClubString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedClubString + PHONE_DESC_ART + EMAIL_DESC_ART + NAME_DESC_ART + ADDRESS_DESC_ART
                        + validExpectedClubString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedClubString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedClubString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedClubString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedClubString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedClubString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedClubString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedClubString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedClubString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Club expectedPerson = new ClubBuilder(ART).withTags().build();
        assertParseSuccess(parser, NAME_DESC_ART + PHONE_DESC_ART + EMAIL_DESC_ART + ADDRESS_DESC_ART,
                new AddClubCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClubCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOOKS + PHONE_DESC_BOOKS + EMAIL_DESC_BOOKS + ADDRESS_DESC_BOOKS,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOOKS + PHONE_DESC_BOOKS + VALID_EMAIL_BOOKS + ADDRESS_DESC_BOOKS,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOOKS + VALID_PHONE_BOOKS + VALID_EMAIL_BOOKS + VALID_ADDRESS_BOOKS,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOOKS + EMAIL_DESC_BOOKS + ADDRESS_DESC_BOOKS
                + TAG_DESC_BIG + TAG_DESC_CASUAL, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOOKS + INVALID_PHONE_DESC + EMAIL_DESC_BOOKS + ADDRESS_DESC_BOOKS
                + TAG_DESC_BIG + TAG_DESC_CASUAL, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOOKS + PHONE_DESC_BOOKS + INVALID_EMAIL_DESC + ADDRESS_DESC_BOOKS
                + TAG_DESC_BIG + TAG_DESC_CASUAL, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOOKS + PHONE_DESC_BOOKS + EMAIL_DESC_BOOKS + ADDRESS_DESC_BOOKS
                + INVALID_TAG_DESC + VALID_TAG_CASUAL, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOOKS + EMAIL_DESC_BOOKS + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOOKS + PHONE_DESC_BOOKS + EMAIL_DESC_BOOKS
                + ADDRESS_DESC_BOOKS + TAG_DESC_BIG + TAG_DESC_CASUAL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClubCommand.MESSAGE_USAGE));
    }
}
