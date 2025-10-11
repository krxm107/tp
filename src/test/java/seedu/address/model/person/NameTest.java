package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import seedu.address.model.field.Name;

public final class NameTest {

    // ---------- constructor & basic validation ----------

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "", "   ", ".", "---", "'''", "....",          // empty / only punctuation
            "John Doe1",                                    // digits not allowed (per validator intent)
            "Jane@Doe", "John Doe*",                        // invalid symbols
            "John\\Doe"                                     // backslash not allowed (forward slash is)
    })
    void constructor_invalidName_throwsIllegalArgumentException(String s) {
        assertThrows(IllegalArgumentException.class, () -> new Name(s));
    }

    @Test
    void constructor_edgeCase_length100_isAllowed() {
        // 98 'A' + " B" => 100 chars
        String s = "A".repeat(98) + " B";
        assertDoesNotThrow(() -> new Name(s));
        assertEquals(s, new Name(s).toString()); // toString returns original
    }

    // ---------- isValidName ----------

    @ParameterizedTest
    @ValueSource(strings = {
            "John Doe",
            "Dr. Jane A. Doe",
            "María-José Carreño Quiñones",
            "O’Connor",
            "Jean-Luc Picard",
            "Ali s/o Ahmad",    // forward slash allowed
            "Tan / Koh",        // slash with spaces
            "  John   Doe  ",
            "Jr."
    })
    void isValidName_valid_returnsTrue(String s) {
        assertTrue(Name.isValidName(s), "Expected valid: " + s);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "", "   ", ".", "---", "'''", "....",
            "John Doe1",
            "Jane@Doe", "John Doe*",
            "John\\Doe"
    })
    void isValidName_invalid_returnsFalse(String s) {
        assertFalse(Name.isValidName(s), "Expected invalid: " + s);
    }

    // ---------- equals / hashCode use normalized name ----------

    @Nested
    class EqualityAndHashCode {

        @Test
        void equal_when_only_spacing_differs() {
            Name a = new Name("  John   Doe ");
            Name b = new Name("John Doe");
            assertEquals(a, b);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        void equal_when_case_differs_if_normalizer_is_caseInsensitive() {
            // If your NameValidator.normalize lowercases for keying, these should be equal.
            // If it preserves case, feel free to delete this test.
            Name a = new Name("JOHN DOE");
            Name b = new Name("john doe");
            assertEquals(a, b);
        }

        @Test
        void notEqual_when_actual_names_differ() {
            Name a = new Name("Jane Doe");
            Name b = new Name("John Doe");
            assertNotEquals(a, b);
        }
    }

    // ---------- toString returns original (not normalized) ----------

    @Test
    void toString_returnsOriginalInput_notNormalized() {
        Name n = new Name("  John   A.   Doe  ");
        // equals/hashCode compare normalized versions,
        // but toString() should return the raw original per your class.
        assertEquals("  John   A.   Doe  ", n.toString());
    }
}
