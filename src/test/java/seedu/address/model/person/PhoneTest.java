//This class was written with the help of ChatGPT.

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import seedu.address.model.field.Phone;
import seedu.address.model.field.validator.PhoneValidator;

public final class PhoneTest {

    // ---------- constructor & basic validation ----------

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "", "   ",                // empty
            "12345",                  // too short (< MIN)
            "1234567890123456",       // too long (> MAX)
            "123-4567",               // non-digit
            "+6581234567",            // plus not allowed per simplified spec
            "(8123)4567",             // brackets not allowed
            "81 23a 4567"             // contains letter
    })
    void constructor_invalid_throwsIllegalArgumentException(String s) {
        assertThrows(IllegalArgumentException.class, () -> new Phone(s));
    }

    @Test
    void constructor_edgeCase_maxLength_isAllowed() {
        String s = "1".repeat(PhoneValidator.MAX_DIGITS);
        assertDoesNotThrow(() -> new Phone(s));
        // If your Phone has getValue(), replace toString() with getValue()
        assertEquals(s, new Phone(s).toString()); // toString returns original
    }

    // ---------- isValidPhone ----------

    @ParameterizedTest
    @ValueSource(strings = {
            "81234567",
            "  8123 4567 ",
            "123456",                 // exactly MIN
            "123456789012345"         // exactly MAX
    })
    void isValidPhone_valid_returnsTrue(String s) {
        assertTrue(Phone.isValidPhone(s), "Expected valid: " + s);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "", "   ",
            "12345",
            "1234567890123456",
            "123-4567",
            "+6581234567",
            "(8123)4567",
            "81 23a 4567"
    })
    void isValidPhone_invalid_returnsFalse(String s) {
        assertFalse(Phone.isValidPhone(s), "Expected invalid: " + s);
    }

    // ---------- equals / hashCode use normalized phone ----------

    @Nested
    class EqualityAndHashCode {

        @Test
        void equal_when_only_whitespace_differs() {
            Phone a = new Phone("  8123  4567 ");
            Phone b = new Phone("81234567");
            assertEquals(a, b);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        void notEqual_when_numbers_differ() {
            Phone a = new Phone("81234567");
            Phone b = new Phone("81234568");
            assertNotEquals(a, b);
        }
    }

    // ---------- toString returns original (not normalized) ----------

    @Test
    void toString_returnsOriginalInput_notNormalized() {
        Phone p = new Phone("  8123  4567 ");
        assertEquals("  8123  4567 ", p.toString());
    }

    // ---------- boundary helpers ----------

    @Test
    void minDigits_accepted() {
        String s = "1".repeat(PhoneValidator.MIN_DIGITS);
        assertTrue(Phone.isValidPhone(s));
    }

    @Test
    void belowMinDigits_rejected() {
        String s = "1".repeat(PhoneValidator.MIN_DIGITS - 1);
        assertFalse(Phone.isValidPhone(s));
    }

    @Test
    void aboveMaxDigits_rejected() {
        String s = "1".repeat(PhoneValidator.MAX_DIGITS + 1);
        assertFalse(Phone.isValidPhone(s));
    }
}
