//Some of the code in this file was written with the help of ChatGPT.

package seedu.address.model.field.validator;

import java.text.Normalizer;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Validates and normalizes person names for add_person.
 * Rules:
 *  - 1–75 chars after stripping.
 *  - Allow ASCII letters A–Z, a–z, digits 0–9, marks, spaces, hyphens, apostrophes, periods, and slashes (/).
 *  - Collapse internal whitespace to single spaces.
 *  - Name key is case-insensitive and space-collapsed; useful for duplicate detection.
 */
public final class NameValidator {

    // Letters (\p{L}), marks (\p{M}), space, hyphen, apostrophe, period, slash.
    private static final Pattern ALLOWED =
            Pattern.compile("^[A-Za-z0-9 .\\-'’/]+$");
    private static final Pattern MULTI_SPACE = Pattern.compile("\\s+");
    private static final int MIN_LEN = 1;
    private static final int MAX_LEN = 75;

    public static final String LENGTH_BOUND_WARNING =
            String.format("Names should have at least %d character "
                    + "and at most %d characters.", MIN_LEN, MAX_LEN);

    private NameValidator() {

    }

    /**
     * Validates and normalizes a raw name string entered by the user.
     * <p>
     * Ensures the name is non-null, within the allowed length range,
     * contains only permitted ASCII characters, and includes at least one letter.
     * <p>
     * The normalized string that results removes unnecessary whitespaces and converts all characters to lowercase.
     *
     * @param raw
     *     the raw input string to validate
     * @return
     *     a {@link ValidationResult} representing success or failure
     */
    public static ValidationResult validate(String raw) {
        if (raw == null) {
            return ValidationResult.fail("Name is required.");
        }

        final String normalized = normalize(raw);
        final int len = normalized.length();

        if (len < MIN_LEN) {
            return ValidationResult.fail("Name cannot be empty after stripping.");
        }
        if (len > MAX_LEN) {
            return ValidationResult.fail("Name exceeds " + MAX_LEN + " characters.");
        }
        if (!ALLOWED.matcher(normalized).matches()) {
            return ValidationResult.fail(
                    "Name contains invalid characters. Allowed: letters, spaces, hyphens (-), "
                            + "apostrophes (' or ’), periods (.), and slashes (/)");
        }
        if (!containsLetter(normalized)) {
            return ValidationResult.fail("Name must contain at least one letter.");
        }
        return ValidationResult.ok(normalized);
    }

    /** Collapse whitespace, strip, and apply Unicode NFKC normalization. */
    public static String normalize(String raw) {
        String s = Normalizer.normalize(Objects.toString(raw, ""), Normalizer.Form.NFKC);
        s = s.strip();
        s = MULTI_SPACE.matcher(s).replaceAll(" ");
        return s;
    }

    /** Case-insensitive, space-collapsed key for duplicate checks. */
    public static String nameKey(String raw) {
        String n = normalize(raw).toLowerCase();
        n = MULTI_SPACE.matcher(n).replaceAll(" ").strip();
        return n;
    }

    private static boolean containsLetter(String s) {
        return s.codePoints().anyMatch(Character::isLetter);
    }

    /**
     * Returns the result of validating an input string in the {@link #validate(String)} method.
     * <p>
     * This class carries 2 pieces of information.
     * <p>
     * The valid field tells us if the validation succeeded or failed.
     * <p>
     * The valueOrMessage field gives us the normalized String if the validation was a success,
     * and a warning message otherwise.
     */
    public static final class ValidationResult {
        private final boolean valid;
        private final String valueOrMessage;

        private ValidationResult(boolean valid, String valueOrMessage) {
            this.valid = valid;
            this.valueOrMessage = valueOrMessage;
        }

        /**
         * @param normalized
         *     The normalized String
         *
         * @return
         *     A successful ValidationResult instance with the normalized String as the value.
         */
        public static ValidationResult ok(String normalized) {
            return new ValidationResult(true, normalized);
        }

        /**
         * @param message
         *     The error message.
         *
         * @return
         *     A failure ValidationResult instance with the error message.
         */
        public static ValidationResult fail(String message) {
            return new ValidationResult(false, message);
        }

        /**
         * Tells us if the validation was a success or a failure.
         */
        public boolean isValid() {
            return valid;
        }

        /**
         * @return
         *     The valueOrMessage field
         */
        public String get() {
            return valueOrMessage;
        }
    }
}
