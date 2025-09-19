package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's Remark in the address book.
 * Guarantees: immutable; is always valid as there are no constraints
 */
public record Remark(String value) {
    /**
     * Constructs a {@code Remark}.
     */
    public Remark {
        requireNonNull(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Remark)) {
            return false;
        }

        final Remark otherAddress = (Remark) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

