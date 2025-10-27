package seedu.address.logic.search.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.membership.Membership;
import seedu.address.model.membership.MembershipStatus;

/**
 * Represents a combined predicate for a <code>MembershipClubCommand</code> or <code>MembershipPersonCommand</code>
 */
public class MembershipStatusPredicate implements Predicate<Membership> {
    private final List<MembershipStatus> statuses;

    public MembershipStatusPredicate(List<MembershipStatus> statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean test(Membership membership) {
        return statuses.stream()
                .anyMatch(status -> membership.getStatus().equals(status));
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

        MembershipStatusPredicate otherMembershipStatusPredicate = (MembershipStatusPredicate) other;
        return statuses.equals(otherMembershipStatusPredicate.statuses);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("statuses", statuses).toString();
    }
}
