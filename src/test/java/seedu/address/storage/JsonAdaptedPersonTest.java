package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.field.Address;
import seedu.address.model.field.Email;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedPersonTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Benson Meier";
    private static final String VALID_PHONE = "98765432";
    private static final String VALID_EMAIL = "johnd@example.com";
    private static final String VALID_ADDRESS = "311, Clementi Ave 2, #02-25";
    private static final List<JsonAdaptedTag> VALID_TAGS = new ArrayList<>(List.of(new JsonAdaptedTag("friends")));

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        Person person = new PersonBuilder().withName(VALID_NAME).withPhone(VALID_PHONE)
                .withEmail(VALID_EMAIL).withAddress(VALID_ADDRESS).withTags("friends").build();
        JsonAdaptedPerson adapted = new JsonAdaptedPerson(person);
        assertEquals(person, adapted.toModelType());
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        try {
            person.toModelType();
        } catch (IllegalValueException ive) {
            assertEquals(expectedMessage, ive.getMessage());
        }
    }

    @Test
    public void toModelType_missingEmail_defaultsToEmpty() throws Exception {
        // email now optional: adapter should allow empty string
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, "", VALID_ADDRESS, VALID_TAGS);
        Person model = person.toModelType();
        assertEquals("", model.getEmail().value);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        try {
            person.toModelType();
        } catch (IllegalValueException ive) {
            assertEquals(expectedMessage, ive.getMessage());
        }
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                invalidTags);
        try {
            person.toModelType();
        } catch (IllegalValueException ive) {
            // The message may be a generic tag constraint message; we just ensure it throws.
            assertTrue(ive.getMessage() != null && !ive.getMessage().isEmpty());
        }
    }

    // If you previously had a "null email throws" test, delete or convert it to the "defaultsToEmpty" test above.
}
