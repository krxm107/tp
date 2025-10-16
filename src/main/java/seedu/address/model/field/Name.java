package seedu.address.model.field;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.field.validator.NameValidator;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain ASCII letters A–Z, a–z, digits 0–9, "
                    + "spaces, hyphens, apostrophes, periods, and slashes (/).";

    public final String fullName;

    private String normalizedFullName = null;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return NameValidator.validate(test).isValid();
    }

    private String getNameKey() {
        if (normalizedFullName == null) {
            normalizedFullName = NameValidator.nameKey(fullName);
        }

        return normalizedFullName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return getNameKey().equals(otherName.getNameKey());
    }

    @Override
    public int hashCode() {
        return getNameKey().hashCode();
    }

}
