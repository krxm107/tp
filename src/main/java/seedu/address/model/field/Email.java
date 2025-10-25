package seedu.address.model.field;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

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

    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format local-part@domain "
            + "and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + "). The local-part may not start or end with any special "
            + "characters.\n"
            + "2. This is followed by a '@' and then a domain name. The domain name is made up of domain labels "
            + "separated by periods.\n"
            + "The domain name must:\n"
            + "    - end with a domain label at least 2 characters long\n"
            + "    - have each domain label start and end with alphanumeric characters\n"
            + "    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.";

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
