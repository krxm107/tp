package seedu.address.model.field;

import static java.util.Objects.requireNonNull;

import java.util.Locale;

/**
 * A special string used for fields.
 *
 * These strings are not only case-insensitive, but are also whitespace-insensitive (defined below).
 *
 * Whitespace-insensitive means that extra whitespaces that aren't leading or trailing do not change the string's value,
 * meaning that "Hello World", "Hello   World" and "Hello       World" have the same value, but "HelloWorld" does not
 * have the same value as the former 3. In addition, any leading or trailing whitespaces do not affect string value.
 * For example, "Hello World" and "  Hello World " have the same value.
 */
public final class FieldString {
    private final String userInputString;

    private String stringToLowerWithoutExcessSpaces;

    /**
     * Constructs the FieldString object.
     *
     * Note that the user will see whatever they typed (i.e. what is passed to the constructor),
     * but internally, the equals checks will be handled gracefully according to the definition of this class.
     */
    public FieldString(final String userInputString) {
        requireNonNull(userInputString);
        this.userInputString = userInputString;
    }

    private String getStringToLowerWithoutExcessSpaces() {
        if (stringToLowerWithoutExcessSpaces == null) {
            stringToLowerWithoutExcessSpaces = userInputString.strip()
                                                              .replaceAll("\\s+", " ")
                                                              .toLowerCase(Locale.ENGLISH);
        }

        return stringToLowerWithoutExcessSpaces;
    }

    /**
     * @return
     * The user-facing display of the FieldString.
     */
    @Override
    public String toString() {
        return userInputString;
    }

    /**
     * @return
     * The hashCode for this String, compliant with .equals(), the whitespace-handling rules, and the case-handling rules of this class.
     */
    @Override
    public int hashCode() {
        return getStringToLowerWithoutExcessSpaces().hashCode();
    }

    /**
     * @param obj the reference object with which to compare.
     * @return
     * Whether these FieldStrings are equal as per the whitespace-handling rules and the case-handling rules of this class.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof FieldString)) {
            return false;
        }

        final FieldString castedObj = (FieldString) (obj);
        return getStringToLowerWithoutExcessSpaces().equals(castedObj.getStringToLowerWithoutExcessSpaces());
    }
}
