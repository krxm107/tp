package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.model.field.Address;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.field.Phone;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class AddPersonCommandParserTest {

    private AddPersonCommandParser parser = new AddPersonCommandParser();

    @Test
    public void parse_requiredNameMissing_failure() {
        // NAME is the only compulsory prefix now
        String validExpected = EMAIL_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB;
        assertParseFailure(parser, validExpected,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_NAME_DESC + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // Email invalid only when provided
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_EMAIL_DESC
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB, Email.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, NAME_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_PHONE_DESC + ADDRESS_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // non-empty preamble -> failure
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + EMAIL_DESC_BOB
                        + PHONE_DESC_BOB + ADDRESS_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
        // multiple phones
        assertParseFailure(parser, validExpectedPersonString + PHONE_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
        // multiple emails (even if optional, duplicates are still an error)
        assertParseFailure(parser, validExpectedPersonString + EMAIL_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));
        // multiple addresses
        assertParseFailure(parser, validExpectedPersonString + ADDRESS_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_missingAllOptional_success() {
        // Only name present should succeed
        Person expected = new PersonBuilder().withName(VALID_NAME_BOB)
                .withEmail("").withPhone("").withAddress("").build();
        assertParseSuccess(parser, NAME_DESC_BOB, new AddPersonCommand(expected));
    }

    @Test
    public void sanity_checkUsageMessage() {
        assertTrue(AddPersonCommand.MESSAGE_USAGE.contains("Optional:"));
    }
}
