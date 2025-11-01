package seedu.address.model.field;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.field.validator.NameValidator;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name implements Comparable<Name> {

    public static final String MESSAGE_CONSTRAINTS =
            "The name is invalid.\n"
            + "Allowed characters: "
            + "A–Z, a–z, digits 0–9, spaces, hyphen -, apostrophe ', period ., "
            + "\nslash /, hash #, comma ',', ampersand &, parentheses (), colon :, semicolon ;, at sign @.\n"
                    + NameValidator.LENGTH_BOUND_WARNING
            + "\nThe name must contain at least 1 letter (A-Z, a-z).";

    public final String fullName;

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

    @Override
    public String toString() {
        return fullName;
    }
    
    public boolean isSameName(Name other) {
        return NameValidator.normalize(fullName).equalsIgnoreCase(NameValidator.normalize(other.fullName));
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
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

    @Override
    public int compareTo(Name name) {
        return this.fullName.toLowerCase().compareTo(name.fullName.toLowerCase());
    }
}
