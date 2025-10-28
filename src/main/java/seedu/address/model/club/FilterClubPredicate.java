package seedu.address.model.club;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Club}'s {@code Name} matches any of the keywords given.
 */

public class FilterClubPredicate implements Predicate<Club> {
    private final List<String> keywords;

    public FilterClubPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Club club) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.matchClubByKeyword(club, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterClubPredicate)) {
            return false;
        }

        FilterClubPredicate otherFilterClubPredicate = (FilterClubPredicate) other;
        return keywords.equals(otherFilterClubPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
