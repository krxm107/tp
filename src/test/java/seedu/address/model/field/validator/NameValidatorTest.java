package seedu.address.model.field.validator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

final class NameValidatorTest {

    private static final String LONG_NAME = "A".repeat(101);

    @ParameterizedTest
    @ValueSource(strings = {
            "John Doe",
            "Dr. Jane A. Doe",
            "María-José Carreño Quiñones",
            "O’Connor",            // curly apostrophe
            "Jean-Luc Picard",
            "Ali s/o Ahmad",       // slash allowed
            "Tan / Koh",           // slash with spaces
            "  John   Doe  ",      // extra spaces
            "Jr."                  // suffix with period
    })
    void valid_names_pass_and_normalize(String input) {
        var res = NameValidator.validate(input);
        assertTrue(res.isValid(), () -> "Expected valid, got: " + res.get());
        String normalized = res.get();
        assertFalse(normalized.startsWith(" "));
        assertFalse(normalized.endsWith(" "));
        assertFalse(normalized.contains("  "));
        // key stability
        assertEquals(NameValidator.nameKey(input), NameValidator.nameKey(normalized));
    }

    @Test
    void normalize_collapses_spaces() {
        assertEquals("John A. Doe", NameValidator.normalize("  John    A.   Doe  "));
    }

    @Test
    void nameKey_is_case_insensitive_and_space_collapsed() {
        assertEquals(NameValidator.nameKey("JOHN   DOE"), NameValidator.nameKey("john doe"));
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void invalid_names_fail(String input) {
        var res = NameValidator.validate(input);
        assertFalse(res.isValid(), "Expected invalid for: \"" + input + "\"");
    }

    // This static method supplies test data
    static Stream<String> invalidNames() {
        return Stream.of(
                "", " ", ".", "---", "'''", "....",
                "A".repeat(101),      // ✅ dynamic value works here
                "John Doe1",          // digits not allowed
                "Jane@Doe",           // invalid symbol
                "John Doe*",          // invalid symbol
                "John\\Doe"           // backslash not allowed
        );
    }

    @Test
    void null_is_invalid() {
        var res = NameValidator.validate(null);
        assertFalse(res.isValid());
    }

    @Test
    void edge_case_length_100() {
        String ninetyEight = "A".repeat(98);
        var res = NameValidator.validate(ninetyEight + " B");
        assertTrue(res.isValid());
        assertEquals(ninetyEight + " B", res.get());
    }
}
