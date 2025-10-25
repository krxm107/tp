//Some of the code in this file was written with the help of ChatGPT.

package seedu.address.model.field.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public final class PhoneValidatorTest {

    // VALID CASES
    @ParameterizedTest
    @ValueSource(strings = {
        "81234567", // typical local number
        "  8123 4567 ", // spaces removed
        "123456", // exactly MIN_DIGITS
        "123456789012345" // exactly MAX_DIGITS
    })
    void validNumbers_returnOk(String raw) {
        var result = PhoneValidator.validate(raw);
        assertTrue(result.isValid(), "Expected valid for: " + raw);
        String normalized = result.get();
        assertEquals(normalized.length(), normalized.strip().length(), "No extra spaces");
        assertTrue(normalized.matches("\\d+"), "Digits only");
        assertTrue(normalized.length() >= PhoneValidator.MIN_DIGITS
                && normalized.length() <= PhoneValidator.MAX_DIGITS);
    }

    @Test
    void normalize_removesAllWhitespace() {
        String n = PhoneValidator.normalize("  8123  4567  ");
        assertEquals("81234567", n);
    }

    // INVALID CASES
    @ParameterizedTest
    @ValueSource(strings = {
        "", "   ", // empty
        "12345", // too short
        "1234567890123456", // too long
    })
    void invalidNumbers_returnFail(String raw) {
        var result = PhoneValidator.validate(raw);
        assertFalse(result.isValid(), "Expected invalid for: " + raw);
    }

    // EDGE CASES
    @Nested
    class EdgeCases {
        @Test
        void minDigitsAccepted() {
            String s = "1".repeat(PhoneValidator.MIN_DIGITS);
            assertTrue(PhoneValidator.validate(s).isValid());
        }

        @Test
        void maxDigitsAccepted() {
            String s = "1".repeat(PhoneValidator.MAX_DIGITS);
            assertTrue(PhoneValidator.validate(s).isValid());
        }

        @Test
        void belowMinDigitsRejected() {
            String s = "1".repeat(PhoneValidator.MIN_DIGITS - 1);
            assertFalse(PhoneValidator.validate(s).isValid());
        }

        @Test
        void aboveMaxDigitsRejected() {
            String s = "1".repeat(PhoneValidator.MAX_DIGITS + 1);
            assertFalse(PhoneValidator.validate(s).isValid());
        }
    }
}
