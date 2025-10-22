//Some of the code in this file was written with the help of ChatGPT.

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import seedu.address.model.field.Name;

public final class NameTest {

    // ---------- constructor & basic validation ----------

    @Test
    void constructorNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "", "   ", ".", "---", "'''", "....", // empty / only punctuation
        "Jane@Doe", "John Doe*", // invalid symbols
        "John\\Doe" // backslash not allowed (forward slash is)
    })
    void constructorInvalidNameThrowsIllegalArgumentException(String s) {
        assertThrows(IllegalArgumentException.class, () -> new Name(s));
    }

    /**
     * Boundary value testing heuristic is applied to test that the max string length of 75 is valid.
     */
    @Test
    void testMaxLengthIsAllowed() {
        // 73 'A' + " B" => 75 chars
        String s = "A".repeat(73) + " B";
        assertDoesNotThrow(() -> new Name(s));
        assertEquals(s, new Name(s).toString()); // toString returns original
    }

    // ---------- isValidName ----------

    @ParameterizedTest
    @ValueSource(strings = {
        "John Doe",
        "Dr. Jane A. Doe",
        "O’Connor",
        "Jean-Luc Picard",
        "Ali s/o Ahmad", // forward slash allowed
        "Tan / Koh", // slash with spaces
        "  John   Doe  ",
        "Jr."
    })
    void isValidNameValidReturnsTrue(String s) {
        assertTrue(Name.isValidName(s), "Expected valid: " + s);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "", "   ", ".", "---", "'''", "....",
        "Jane@Doe", "John Doe*",
        "John\\Doe"
    })
    void isValidNameInvalidReturnsFalse(String s) {
        assertFalse(Name.isValidName(s), "Expected invalid: " + s);
    }

    // ---------- equals / hashCode use normalized name ----------

    @Nested
    class EqualityAndHashCode {

        @Test
        void assertExtraSpacesBreaksEquality() {
            Name a = new Name("  John   Doe ");
            Name b = new Name("John Doe");
            assertNotEquals(a, b);
            assertNotEquals(a.hashCode(), b.hashCode());
        }

        @Test
        void notEqualIfCaseDiffers() {
            Name a = new Name("JOHN DOE");
            Name b = new Name("john doe");
            assertNotEquals(a, b);
        }

        @Test
        void notEqualWhenActualNamesDiffer() {
            Name a = new Name("Jane Doe");
            Name b = new Name("John Doe");
            assertNotEquals(a, b);
        }
    }

    // ---------- toString returns original (not normalized) ----------
    @Test
    void toStringReturnsOriginalInputNotNormalized() {
        Name n = new Name("  John   A.   Doe  ");
        assertEquals("  John   A.   Doe  ", n.toString());
    }
}
