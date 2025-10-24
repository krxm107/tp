package seedu.address.model.field;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.model.field.validator.AddressValidator;

/**
 * Represents a Person's address in the address book.
 * <p>
 * This field is optional — if no address is provided, an empty {@code Address}
 * object will be created. Otherwise, the value must pass validation rules.
 * </p>
 * Guarantees: immutable; valid if non-empty.
 */
public class Address {

    public static final String MESSAGE_CONSTRAINTS = "Address must consist of "
            + "only letters A-Z a-z, digits, whitespace, \n"
            + "hyphens, apostrophes, periods, slashes, hash signs #, \n"
            + "commas, ampersands, parentheses, semicolons, "
            + "or colons."
            + "\n\nAn Address must be at most 150 characters long.";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address
     *     A valid address, or an empty/whitespace string if optional.
     *     <ul>
     *         <li>If {@code address} is null or blank, an empty Address is created.</li>
     *         <li>Otherwise, it must satisfy {@link #isValidAddress(String)}.</li>
     *     </ul>
     */
    public Address(String address) {
        if (address == null || address.strip().isEmpty()) {
            this.value = "";
            return;
        }

        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);

        this.value = AddressValidator.validateOrThrow(address, true);
    }

    /**
     * Returns true if the given string is a valid address.
     * An empty string is treated as invalid (but constructible as optional).
     */
    public static boolean isValidAddress(String test) {
        return AddressValidator.isValid(test, true);
    }

    /** Returns true if this address field was provided by the user. */
    public boolean isPresent() {
        return value != null && !value.isEmpty();
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
        if (!(other instanceof Address)) {
            return false;
        }

        Address otherAddress = (Address) other;
        return Objects.equals(value, otherAddress.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
