package seedu.address.model.field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "example.com";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null -> NPE
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // empty string -> valid (email optional)
        assertTrue(Email.isValidEmail(""));

        // invalid emails
        assertFalse(Email.isValidEmail("@"));
        assertFalse(Email.isValidEmail("a@"));
        assertFalse(Email.isValidEmail("@b"));
        assertFalse(Email.isValidEmail("a@b"));        // no dot
        assertFalse(Email.isValidEmail("a b@c.com"));  // spaces

        // valid emails
        assertTrue(Email.isValidEmail(" "));
        assertTrue(Email.isValidEmail("alice@example.com"));
        assertTrue(Email.isValidEmail("ALICE@EXAMPLE.COM"));
        assertTrue(Email.isValidEmail("a.b-c_d+z@ex-ample.co.uk"));
    }
}
