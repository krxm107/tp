package seedu.address.logic.search.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.field.Searchable;
import seedu.address.model.membership.MembershipStatus;

/**
 * Tests that a {@code Searchable}'s {@code Membership}s match any of the <code>MembershipStatus</code>es given.
 */
public class StatusesMatchPredicate<T extends Searchable> implements Predicate<T> {
    private final List<MembershipStatus> statuses;

    public StatusesMatchPredicate(List<MembershipStatus> statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean test(Searchable searchable) {
        return searchable.getMemberships().stream().anyMatch(new MembershipStatusPredicate(statuses));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatusesMatchPredicate<?>)) {
            return false;
        }

        StatusesMatchPredicate<?> otherStatusesMatchPredicate = (StatusesMatchPredicate<?>) other;
        return statuses.equals(otherStatusesMatchPredicate.statuses);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("statuses", statuses).toString();
    }
}
