package seedu.address.model.club;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Tests whether any of a {@code Club}'s {@code Tag}s matches any of the keywords given.
 */
public class ClubContainsTagsPredicate implements Predicate<Club> {
    private final List<String> tags;

    public ClubContainsTagsPredicate(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Club club) {
        return club.getTags().stream().anyMatch(new TagContainsKeywordsPredicate(tags));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClubContainsTagsPredicate)) {
            return false;
        }

        ClubContainsTagsPredicate otherClubContainsTagsPredicate = (ClubContainsTagsPredicate) other;
        return tags.equals(otherClubContainsTagsPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tags", tags).toString();
    }
}
