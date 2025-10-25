package seedu.address.model.field;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {
    
    public static final String MESSAGE_CONSTRAINTS =
            "Emails should start with a letter or a digit, "
            + "and end with a letter or a digit.\n\n"
            + "They should be of the format LOCAL_PART@DOMAIN_LABEL.DOMAIN_NAME (e.g. example@domain.com).\n\n"
            + "Each of LOCAL_PART, DOMAIN_LABEL and DOMAIN_NAME should start and end with a digit.\n\n"
            + "Each of LOCAL_PART, DOMAIN_LABEL and DOMAIN_NAME should contain one or more letters or digits.\n\n"
            + "The email should contain at most 150 characters and "
            + "LOCAL_PART should contain at most 64 characters.\n\n"
            + "DOMAIN_NAME should contain at least 2 characters.";

    private static final String SPECIAL_CHARACTERS = "+_.-";

    private static final String LOCAL_PART_REGEX = "[A-Za-z0-9]+([._+-][A-Za-z0-9]+)*";
    private static final String DOMAIN_LABEL_REGEX = "[A-Za-z0-9]+(-[A-Za-z0-9]+)*";

    private static final String DOMAIN_REGEX = DOMAIN_LABEL_REGEX
            + "(\\." + DOMAIN_LABEL_REGEX
            + ")*\\.[A-Za-z0-9]{2,}";

    private static final String EMAIL_REGEX = "^"
            + LOCAL_PART_REGEX
            + "@" + DOMAIN_REGEX + "$";

    private static final int MAX_EMAIL_LENGTH = 150;
    private static final int MAX_LOCAL_PART_LENGTH = 64;

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        if (test == null) {
            return false;
        }

        if (test.length() > MAX_EMAIL_LENGTH) {
            return false;
        }

        if (!test.matches(EMAIL_REGEX)) {
            return false;
        }

        final String[] emailParts = test.split("@", -1);
        if (emailParts.length != 2) {
            return false;
        }

        final String emailLocalPart = emailParts[0];
        final String emailDomainPart = emailParts[1];
        return isValidEmailLocalPart(emailLocalPart) && isValidEmailDomainPart(emailDomainPart);
    }

    private static boolean isValidEmailLocalPart(final String emailLocalPart) {
        if (emailLocalPart == null) {
            return false;
        }

        if (emailLocalPart.length() > MAX_LOCAL_PART_LENGTH) {
            return false;
        }

        return emailLocalPart.matches(LOCAL_PART_REGEX);
    }

    private static boolean isValidEmailDomainPart(final String emailDomainPart) {
        if (emailDomainPart == null) {
            return false;
        }

        return emailDomainPart.matches(DOMAIN_REGEX);
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
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
