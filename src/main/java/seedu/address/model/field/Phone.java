package seedu.address.model.field;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.model.field.validator.PhoneValidator;

/**
 * Represents a Person's phone number in the address book.
 * <p>
 * This field is optional — if no phone number is provided, an empty {@code Phone}
 * object will be created. Otherwise, the value must pass {@link PhoneValidator} rules.
 * </p>
 * Guarantees: immutable; valid if non-empty.
 */
public class Phone implements Comparable<Phone> {

    public static final String MESSAGE_CONSTRAINTS =
            "Phones must contain a minimum of 6 non-whitespace characters "
                    + "and a maximum of 15 non-whitespace characters.";

    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number, or an empty/whitespace string if optional.
     *              <ul>
     *                  <li>If {@code phone} is null or blank, an empty Phone is created.</li>
     *                  <li>Otherwise, it must satisfy {@link #isValidPhone(String)}.</li>
     *              </ul>
     */
    public Phone(String phone) {
        if (phone == null || phone.strip().isEmpty()) {
            this.value = "";
            return;
        }
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        this.value = PhoneValidator.normalize(phone);
    }

    /**
     * Returns true if the given string is a valid phone number.
     * An empty string is treated as invalid (but constructible as optional).
     */
    public static boolean isValidPhone(String test) {
        return PhoneValidator.validate(test).isValid();
    }

    /**
     * Returns true if the phone contains a character that isn't numeric or a whitespace.
     */
    public boolean containsNonNumericNonSpaceCharacter() {
        for (int i = 0; i < value.length(); i++) {
            final char currChar = value.charAt(i);
            if ('0' <= currChar && currChar <= '9') {
                continue;
            }

            if (Character.isSpaceChar((int) currChar)) {
                continue;
            }

            return true;
        }

        return false;
    }

    /** Returns true if this phone field was provided by the user. */
    public boolean isPresent() {
        return value != null && !value.isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return Objects.equals(value, otherPhone.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public int compareTo(Phone phone) {
        return Integer.getInteger(this.value)
                .compareTo(Integer.getInteger(phone.value));
    }
}
