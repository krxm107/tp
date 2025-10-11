package seedu.address.model.field.validator;

import java.text.Normalizer;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Validates and normalizes person names for add_person.
 * Rules:
 *  - 1–100 chars after trimming.
 *  - Allow Unicode letters, marks, spaces, hyphens, apostrophes, periods, and slashes (/).
 *  - Collapse internal whitespace to single spaces.
 *  - Name key is case-insensitive and space-collapsed; useful for duplicate detection.
 */
public final class NameValidator {

    // Letters (\p{L}), marks (\p{M}), space, hyphen, apostrophe, period, slash.
    private static final Pattern ALLOWED =
            Pattern.compile("^[\\p{L}\\p{M} .\\-'’/]+$");
    private static final Pattern MULTI_SPACE = Pattern.compile("\\s+");
    private static final int MIN_LEN = 1;
    private static final int MAX_LEN = 100;

    private NameValidator() {}

    public static ValidationResult validate(String raw) {
        if (raw == null) {
            return ValidationResult.fail("Name is required.");
        }

        final String normalized = normalize(raw);
        final int len = normalized.length();

        if (len < MIN_LEN) {
            return ValidationResult.fail("Name cannot be empty after trimming.");
        }
        if (len > MAX_LEN) {
            return ValidationResult.fail("Name exceeds " + MAX_LEN + " characters.");
        }
        if (!ALLOWED.matcher(normalized).matches()) {
            return ValidationResult.fail(
                    "Name contains invalid characters. Allowed: letters, spaces, hyphens (-), apostrophes (' or ’), periods (.), and slashes (/)");
        }
        if (!containsLetter(normalized)) {
            return ValidationResult.fail("Name must contain at least one letter.");
        }
        return ValidationResult.ok(normalized);
    }

    /** Collapse whitespace, trim, and apply Unicode NFKC normalization. */
    public static String normalize(String raw) {
        String s = Normalizer.normalize(Objects.toString(raw, ""), Normalizer.Form.NFKC);
        s = s.trim();
        s = MULTI_SPACE.matcher(s).replaceAll(" ");
        return s;
    }

    /** Case-insensitive, space-collapsed key for duplicate checks. */
    public static String nameKey(String raw) {
        String n = normalize(raw).toLowerCase();
        n = MULTI_SPACE.matcher(n).replaceAll(" ").trim();
        return n;
    }

    private static boolean containsLetter(String s) {
        return s.codePoints().anyMatch(Character::isLetter);
    }

    // --- Validation result holder ---
    public static final class ValidationResult {
        private final boolean valid;
        private final String valueOrMessage;

        private ValidationResult(boolean valid, String valueOrMessage) {
            this.valid = valid;
            this.valueOrMessage = valueOrMessage;
        }

        private static ValidationResult ok(String normalized) {
            return new ValidationResult(true, normalized);
        }

        private static ValidationResult fail(String message) {
            return new ValidationResult(false, message);
        }

        public boolean isValid() { return valid; }
        public String get() { return valueOrMessage; } // normalized or error message
    }
}
