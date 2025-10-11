//This class was written with the help of ChatGPT.

package seedu.address.model.field.validator;

import java.text.Normalizer;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Validates and normalizes phone numbers for add_person.
 * <p>
 * Rules:
 * - Only digits are allowed.
 * - All whitespace is removed.
 * - Length must be between MIN_DIGITS and MAX_DIGITS inclusive.
 */
public final class PhoneValidator {

    public static final int MIN_DIGITS = 6;
    public static final int MAX_DIGITS = 15;

    private static final Pattern DIGITS_ONLY = Pattern.compile("^\\d+$");
    private static final Pattern WS = Pattern.compile("\\s+");

    private PhoneValidator() {

    }

    public static ValidationResult validate(String raw) {
        if (raw == null) {
            return ValidationResult.fail("Phone number is required.");
        }
        String normalized = normalize(raw);

        if (normalized.isEmpty()) {
            return ValidationResult.fail("Phone number cannot be empty.");
        }
        if (!DIGITS_ONLY.matcher(normalized).matches()) {
            return ValidationResult.fail("Phone number must contain only digits.");
        }
        if (normalized.length() < MIN_DIGITS) {
            return ValidationResult.fail("Phone number must have at least " + MIN_DIGITS + " digits.");
        }
        if (normalized.length() > MAX_DIGITS) {
            return ValidationResult.fail("Phone number must have at most " + MAX_DIGITS + " digits.");
        }

        return ValidationResult.ok(normalized);
    }

    public static String normalize(String raw) {
        String s = Normalizer.normalize(Objects.toString(raw, ""), Normalizer.Form.NFKC);
        return WS.matcher(s).replaceAll("");
    }

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

        public boolean isValid() {
            return valid;
        }

        public String get() {
            return valueOrMessage;
        }
    }
}