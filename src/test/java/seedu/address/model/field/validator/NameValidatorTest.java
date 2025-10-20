//Some of the code in this file was written with the help of ChatGPT.

package seedu.address.model.field.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public final class NameValidatorTest {

    private static final String LONG_NAME = "A".repeat(101);

    @ParameterizedTest
    @ValueSource(strings = {
        "John Doe",
        "Dr. Jane A. Doe",
        "O’Connor", // curly apostrophe
        "Jean-Luc Picard",
        "Ali s/o Ahmad", // slash allowed
        "Tan / Koh", // slash with spaces
        "  John   Doe  ", // extra spaces
        "Jr.", // suffix with period,
    })
    void validNamesPassAndNormalize(String input) {
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
    void normalizeCollapsesSpaces() {
        assertEquals("John A. Doe", NameValidator.normalize("  John    A.   Doe  "));
    }

    @Test
    void nameKeyIsCaseInsensitiveAndSpaceCollapsed() {
        assertEquals(NameValidator.nameKey("JOHN   DOE"), NameValidator.nameKey("john doe"));
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void invalidNamesFail(String input) {
        var res = NameValidator.validate(input);
        assertFalse(res.isValid(), "Expected invalid for: \"" + input + "\"");
    }

    // This static method supplies test data
    static Stream<String> invalidNames() {
        return Stream.of(
                "", " ", ".", "---", "'''", "....",
                "A".repeat(101), // ✅ dynamic value works here
                "Jane@Doe", // invalid symbol
                "John Doe*", // invalid symbol
                "John\\Doe" // backslash not allowed
        );
    }

    @Test
    void nullIsInvalid() {
        var res = NameValidator.validate(null);
        assertFalse(res.isValid());
    }

    /**
     * Boundary value testing heuristic is applied to test that the max string length of 75 is valid.
     */
    @Test
    void boundaryValueTestMaxLength() {
        String ninetyEight = "A".repeat(73);
        var res = NameValidator.validate(ninetyEight + " B");
        assertTrue(res.isValid());
        assertEquals(ninetyEight + " B", res.get());
    }
}
