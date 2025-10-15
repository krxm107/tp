package seedu.address.model.membership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.field.Email;
import seedu.address.model.field.Name;
import seedu.address.model.membership.Membership;
import seedu.address.model.membership.exceptions.DuplicateMembershipException;
import seedu.address.model.membership.exceptions.MembershipNotFoundException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class UniqueMembershipList implements Iterable<Membership> {
    private final ObservableList<Membership> internalList = FXCollections.observableArrayList();
    private final ObservableList<Membership> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent membership as the given argument.
     */
    public boolean contains(Membership toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a membership to the list.
     * The membership must not already exist in the list.
     */
    public void add(Membership toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMembershipException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the membership {@code target} in the list with {@code editedMembership}.
     * {@code target} must exist in the list.
     * The membership identity of {@code editedMembership} must not be the same as another existing membership in the list.
     */
    public void setMembership(Membership target, Membership editedMembership) {
        requireAllNonNull(target, editedMembership);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MembershipNotFoundException();
        }

        if (!target.equals(editedMembership) && contains(editedMembership)) {
            throw new DuplicateMembershipException();
        }

        internalList.set(index, editedMembership);
    }

    /**
     * Removes the equivalent membership from the list.
     * The membership must exist in the list.
     */
    public void remove(Membership toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MembershipNotFoundException();
        }
    }

    public void setMemberships(UniqueMembershipList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code memberships}.
     * {@code memberships} must not contain duplicate memberships.
     */
    public void setMemberships(List<Membership> memberships) {
        requireAllNonNull(memberships);
        if (!membershipsAreUnique(memberships)) {
            throw new DuplicateMembershipException();
        }

        internalList.setAll(memberships);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Membership> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Membership> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueMembershipList)) {
            return false;
        }

        UniqueMembershipList otherUniqueMembershipList = (UniqueMembershipList) other;
        return internalList.equals(otherUniqueMembershipList.internalList);
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
     * Returns true if {@code memberships} contains only unique memberships.
     */
    private boolean membershipsAreUnique(List<Membership> memberships) {
        for (int i = 0; i < memberships.size() - 1; i++) {
            for (int j = i + 1; j < memberships.size(); j++) {
                if (memberships.get(i).equals(memberships.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
