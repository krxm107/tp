//Some of the code in this file was written with the help of ChatGPT.

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import seedu.address.model.field.Phone;
import seedu.address.model.field.validator.PhoneValidator;

public final class PhoneTest {

    // ---------- constructor & basic validation ----------

    @Test
    void constructorEdgeCaseMaxLengthIsAllowed() {
        String s = "1".repeat(PhoneValidator.MAX_DIGITS);
        assertDoesNotThrow(() -> new Phone(s));
        assertEquals(s, new Phone(s).toString()); // toString returns original
    }

    // ---------- isValidPhone ----------

    @ParameterizedTest
    @ValueSource(strings = {
        "81234567",
        "  8123 4567 ",
        "123456", // exactly MIN
        "123456789012345" // exactly MAX
    })
    void isValidPhoneValidReturnsTrue(String s) {
        assertTrue(Phone.isValidPhone(s), "Expected valid: " + s);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "", "   ",
        "12345",
        "1234567890123456",
    })
    void isValidPhoneInvalidReturnsFalse(String s) {
        assertFalse(Phone.isValidPhone(s), "Expected invalid: " + s);
    }

    // ---------- equals / hashCode use normalized phone ----------

    @Nested
    class EqualityAndHashCode {

        @Test
        void equalWhenOnlyWhitespaceDiffers() {
            Phone a = new Phone("  8123  4567 ");
            Phone b = new Phone("81234567");
            assertEquals(a, b);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        void notEqualWhenNumbersDiffer() {
            Phone a = new Phone("81234567");
            Phone b = new Phone("81234568");
            assertNotEquals(a, b);
        }
    }

    // ---------- toString returns normalized String ----------

    @Test
    void toStringReturnsOriginalInputNormalized() {
        Phone p = new Phone("  8123  4567 ");
        assertEquals("81234567", p.toString());
    }

    // ---------- boundary helpers ----------

    @Test
    void minDigitsAccepted() {
        String s = "1".repeat(PhoneValidator.MIN_DIGITS);
        assertTrue(Phone.isValidPhone(s));
    }

    @Test
    void belowMinDigitsRejected() {
        String s = "1".repeat(PhoneValidator.MIN_DIGITS - 1);
        assertFalse(Phone.isValidPhone(s));
    }

    @Test
    void aboveMaxDigitsRejected() {
        String s = "1".repeat(PhoneValidator.MAX_DIGITS + 1);
        assertFalse(Phone.isValidPhone(s));
    }

    /**
     * Unit tests for {@link Phone}.
     * Includes validation and optional (empty) phone behavior.
     */
    @Test
    void emptyPhoneAllowedAndDisplaysEmpty() {
        Phone p = new Phone("");
        assertEquals("", p.toString());
        assertFalse(p.isPresent());
    }
}
