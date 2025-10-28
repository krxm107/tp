// Some of the code in this file was written with the help of ChatGPT

package seedu.address.model.field.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class AddressValidatorTest {

    @Nested
    @DisplayName("isValid(address) with allowEmpty=false")
    class DisallowEmpty {

        @Test
        void valid_basicExamples() {
            assertTrue(AddressValidator.isValid("123 Main Street"));
            assertTrue(AddressValidator.isValid("Blk 123 #05-67, Orchard Road"));
            assertTrue(AddressValidator.isValid("Apt. 3-2 (Tower B)"));
            assertTrue(AddressValidator.isValid("No. 10-1/2 King's Rd: Unit #07-01 & #07-02"));
            assertTrue(AddressValidator.isValid("Road-Name'with.mixed/Chars#123, City"));
        }

        @Test
        void invalid_containsDisallowedChars() {
            assertFalse(AddressValidator.isValid("Unit !? 12")); // @ is not allowed
            assertFalse(AddressValidator.isValid("Street * Name")); // * is not allowed
            assertFalse(AddressValidator.isValid("Blk 1 ~ 2")); // ~ not allowed
        }

        @Test
        void invalid_lengthOver150() {
            String over150 = "A".repeat(AddressValidator.MAX_LENGTH + 1);
            assertFalse(AddressValidator.isValid(over150));
        }

        @Test
        void invalid_nullOrEmpty() {
            assertFalse(AddressValidator.isValid(null));
            assertFalse(AddressValidator.isValid(""));
            assertFalse(AddressValidator.isValid("   "));
        }

        @Test
        void boundary_lengthExactly150_isValid() {
            String exactly150 = "A".repeat(AddressValidator.MAX_LENGTH);
            assertTrue(AddressValidator.isValid(exactly150));
        }

        @Test
        void validateOrThrow_throwsOnInvalid() {
            assertThrows(IllegalArgumentException.class, () ->
                    AddressValidator.validateOrThrow("Unit !? 12", false));
        }

        @Test
        void validateOrThrow_returnsTrimmedOnValid() {
            String trimmed = AddressValidator.validateOrThrow("  123 Main Street  ", false);
            assertEquals("123 Main Street", trimmed);
        }
    }

    @Nested
    @DisplayName("isValid(address, allowEmpty=true)")
    class AllowEmpty {

        @Test
        void emptyOrNull_allowed() {
            assertTrue(AddressValidator.isValid(null, true));
            assertTrue(AddressValidator.isValid("", true));
            assertTrue(AddressValidator.isValid("   ", true));
        }

        @Test
        void stillRejects_badChars_orTooLong() {
            assertFalse(AddressValidator.isValid("Unit !? 12", true)); // bad char
            String over150 = "A".repeat(AddressValidator.MAX_LENGTH + 1);
            assertFalse(AddressValidator.isValid(over150, true)); // too long
        }
    }
}
