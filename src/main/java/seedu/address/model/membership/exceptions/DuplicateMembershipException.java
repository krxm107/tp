package seedu.address.model.membership.exceptions;

/**
 * Signals that the operation will result in duplicate Memberships (Memberships are considered duplicates if they have the same
 * identity).
 */
public class DuplicateMembershipException extends RuntimeException {
    public DuplicateMembershipException() {
        super("Operation would result in duplicate memberships");
    }
}
