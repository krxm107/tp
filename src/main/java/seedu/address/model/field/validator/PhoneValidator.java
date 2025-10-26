//Some of the code in this file was written with the help of ChatGPT.

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

    private static final Pattern ALLOWED =
            Pattern.compile("^[A-Za-z0-9\\s\\-'.#/,&():;@]+$");

    private static final Pattern WS = Pattern.compile("\\s+");

    private PhoneValidator() {

    }

    /**
     * Validates and normalizes a raw name string entered by the user.
     * <p>
     * Ensures the name is non-null, within the allowed length range,
     * and contains only digits and spaces.
     * <p>
     * The normalized string that results removes unnecessary whitespaces.
     *
     * @param raw
     *     the raw input string to validate
     * @return
     *     a {@link NameValidator.ValidationResult} representing success or failure
     */
    public static ValidationResult validate(String raw) {
        if (raw == null) {
            return ValidationResult.fail("Phone number is required.");
        }
        String normalized = normalize(raw);

        if (normalized.isEmpty()) {
            return ValidationResult.fail("Phone number cannot be empty.");
        }
        if (!ALLOWED.matcher(normalized).matches()) {
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

    /** Remove unnecessary whitespaces. */
    public static String normalize(String raw) {
        String s = Normalizer.normalize(Objects.toString(raw, ""), Normalizer.Form.NFKC);
        return WS.matcher(s).replaceAll("");
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
