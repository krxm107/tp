package seedu.address.model.club;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import seedu.address.model.club.exceptions.ClubNotFoundException;
import seedu.address.model.club.exceptions.DuplicateClubException;
import seedu.address.model.field.Name;
import seedu.address.model.membership.Membership;

/**
 * A list of clubs that enforces uniqueness between its elements and does not allow nulls.
 * A club is considered unique by comparing using {@code Club#isSameClub(Club)}. As such, adding and updating of
 * clubs uses Club#isSameClub(Club) for equality so as to ensure that the club being added or updated is
 * unique in terms of identity in the UniqueClubList. However, the removal of a club uses Club#equals(Object) so
 * as to ensure that the club with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Club#isSameClub(Club)
 */
public class UniqueClubList implements Iterable<Club> {

    private final Callback<Club, Observable[]> extractor = club -> {
        // Create a stream of all the status properties from all memberships
        Observable[] membershipStatuses = club.getMemberships().stream()
                .map(Membership::statusProperty)
                .toArray(Observable[]::new);

        // Create a final observable array that contains:
        // 1. The membership set itself (for additions/removals)
        // 2. All the individual status properties (for status changes)
        return Stream.concat(
                Stream.of(club.getMemberships()),
                Stream.of(membershipStatuses)
        ).toArray(Observable[]::new);
    };

    private final ObservableList<Club> internalList = FXCollections.observableArrayList(extractor);
    private final ObservableList<Club> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent club as the given argument.
     */
    public boolean contains(Club toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameClub);
    }

    /**
     * Adds a club to the list.
     * The club must not already exist in the list.
     */
    public void add(Club toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateClubException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the club {@code target} in the list with {@code editedClub}.
     * {@code target} must exist in the list.
     * The club identity of {@code editedClub} must not be the same as another existing club in the list.
     */
    public void setClub(Club target, Club editedClub) {
        requireAllNonNull(target, editedClub);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ClubNotFoundException();
        }

        if (!target.isSameClub(editedClub) && contains(editedClub)) {
            throw new DuplicateClubException();
        }

        internalList.set(index, editedClub);
    }

    /**
     * Removes the equivalent club from the list.
     * The club must exist in the list.
     */
    public void remove(Club toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ClubNotFoundException();
        }
    }

    public void setClubs(UniqueClubList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code clubs}.
     * {@code clubs} must not contain duplicate clubs.
     */
    public void setClubs(List<Club> clubs) {
        requireAllNonNull(clubs);
        if (!clubsAreUnique(clubs)) {
            throw new DuplicateClubException();
        }

        internalList.setAll(clubs);
    }

    public Optional<Club> getClub(Name name) {
        return internalList.stream()
                .filter(person ->
                        person.getName().equals(name)
                )
                .findFirst();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Club> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Club> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueClubList)) {
            return false;
        }

        UniqueClubList otherUniqueClubList = (UniqueClubList) other;
        return internalList.equals(otherUniqueClubList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code clubs} contains only unique clubs.
     */
    private boolean clubsAreUnique(List<Club> clubs) {
        for (int i = 0; i < clubs.size() - 1; i++) {
            for (int j = i + 1; j < clubs.size(); j++) {
                if (clubs.get(i).isSameClub(clubs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
