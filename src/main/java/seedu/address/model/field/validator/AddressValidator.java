// Some of the code in this file was written with the help of ChatGPT

package seedu.address.model.field.validator;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Validates Address strings against length and character constraints.
 */
public final class AddressValidator {

    /** Max length for address. */
    public static final int MAX_LENGTH = 150;

    /**
     * Allowed characters:
     *  A–Z, a–z, digits 0–9, spaces, hyphen -, apostrophe ', period .,
     *  slash /, hash #, comma ',', ampersand &, parentheses (), colon :, semicolon ;
     *
     * IMPORTANT: This is the exact pattern requested.
     */
    public static final String ADDRESS_ALLOWED_CHARS_REGEX = "^[A-Za-z0-9\\s\\-'.#/,&():;]*$";

    private static final Pattern ALLOWED = Pattern.compile(ADDRESS_ALLOWED_CHARS_REGEX);

    private AddressValidator() {
        // utility class
    }

    /**
     * Returns true if {@code address} is valid under the constraints.
     * Empty or null is treated as INVALID by default.
     */
    public static boolean isValid(String address) {
        return isValid(address, /*allowEmpty*/ false);
    }

    /**
     * Returns true if {@code address} is valid under the constraints.
     *
     * @param address    the input string to check
     * @param allowEmpty if true, null/blank ("") are treated as valid (useful if the field is optional upstream)
     */
    public static boolean isValid(String address, boolean allowEmpty) {
        if (address == null) {
            return allowEmpty;
        }
        final String s = address.trim();

        if (s.isEmpty()) {
            return allowEmpty;
        }
        if (s.length() > MAX_LENGTH) {
            return false;
        }
        return ALLOWED.matcher(s).matches();
    }

    /**
     * Validates and returns the trimmed address, or throws {@link IllegalArgumentException}.
     */
    public static String validateOrThrow(String address, boolean allowEmpty) {
        if (!isValid(address, allowEmpty)) {
            final String actualLen = (address == null) ? "null" : String.valueOf(address.trim().length());
            throw new IllegalArgumentException(
                    "Invalid address. Must be <= " + MAX_LENGTH + " chars and match " + ADDRESS_ALLOWED_CHARS_REGEX
                            + ". (length=" + actualLen + ")"
            );
        }
        return address == null ? null : address.trim();
    }

    /**
     * Equality method for comparing validators.
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof AddressValidator; // stateless utility
    }

    @Override
    public int hashCode() {
        return Objects.hash(AddressValidator.class);
    }
}
