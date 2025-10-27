package seedu.address.logic.parser;

import java.util.function.Predicate;

import seedu.address.model.membership.Membership;
import seedu.address.model.membership.MembershipStatus;

/**
 * Represents a combined predicate for a <code>MembershipClubCommand</code> or <code>ListMembershipCommand</code>
 */
public class MembershipStatusPredicate implements Predicate<Membership> {
    private boolean standard = true;
    private boolean active = false;
    private boolean canceled = false;
    private boolean expired = false;
    private boolean pending = false;

    private Predicate<Membership> predicate = membership -> true;

    /**
     * Adds a predicate for a particular membership status indicated by the keyword
     */
    public void addPredicate(String keyword) {
        if (standard) {
            standard = false;
            predicate = membership -> false;
        }

        switch (keyword) {
        case "a" -> setActive();
        case "c" -> setCanceled();
        case "e" -> setExpired();
        case "p" -> setPending();
        default -> setNothing();
        }
    }

    @Override
    public boolean test(Membership membership) {
        return predicate.test(membership);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MembershipStatusPredicate)) {
            return false;
        }

        MembershipStatusPredicate otherPredicate = (MembershipStatusPredicate) other;
        return standard == otherPredicate.standard
                && active == otherPredicate.active
                && canceled == otherPredicate.canceled
                && expired == otherPredicate.expired
                && pending == otherPredicate.pending;
    }

    private void setActive() {
        active = true;
        predicate = predicate.or(membership ->
                membership.getStatus().equals(MembershipStatus.ACTIVE));
    }

    private void setCanceled() {
        canceled = true;
        predicate = predicate.or(membership ->
                membership.getStatus().equals(MembershipStatus.CANCELLED));
    }

    private void setExpired() {
        expired = true;
        predicate = predicate.or(membership ->
                membership.getStatus().equals(MembershipStatus.EXPIRED));
    }

    private void setPending() {
        pending = true;
        predicate = predicate.or(membership ->
                membership.getStatus().equals(MembershipStatus.PENDING_CANCELLATION));
    }

    private void setNothing() {}
}
