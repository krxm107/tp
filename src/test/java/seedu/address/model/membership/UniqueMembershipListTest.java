package seedu.address.model.membership;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.membership.exceptions.DuplicateMembershipException;
import seedu.address.model.membership.exceptions.MembershipNotFoundException;
import seedu.address.testutil.MembershipBuilder;

public class UniqueMembershipListTest {

    private final UniqueMembershipList uniqueMembershipList = new UniqueMembershipList();
    private final Membership membership = new MembershipBuilder().build();

    @Test
    public void contains_nullMembership_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMembershipList.contains(null));
    }

    @Test
    public void contains_membershipNotInList_returnsFalse() {
        assertFalse(uniqueMembershipList.contains(membership));
    }

    @Test
    public void contains_membershipInList_returnsTrue() {
        uniqueMembershipList.add(membership);
        assertTrue(uniqueMembershipList.contains(membership));
    }

    @Test
    public void add_nullMembership_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMembershipList.add(null));
    }

    @Test
    public void add_duplicateMembership_throwsDuplicateMembershipException() {
        uniqueMembershipList.add(membership);
        assertThrows(DuplicateMembershipException.class, () -> uniqueMembershipList.add(membership));
    }

    @Test
    public void setMembership_nullTargetMembership_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMembershipList.setMembership(null, membership));
    }

    @Test
    public void setMembership_nullEditedMembership_throwsNullPointerException() {
        uniqueMembershipList.add(membership);
        assertThrows(NullPointerException.class, () -> uniqueMembershipList.setMembership(membership, null));
    }

    @Test
    public void setMembership_targetMembershipNotInList_throwsMembershipNotFoundException() {
        assertThrows(MembershipNotFoundException.class, ()
                -> uniqueMembershipList.setMembership(membership, membership));
    }

    @Test
    public void setMembership_editedMembershipIsSameMembership_success() {
        uniqueMembershipList.add(membership);
        uniqueMembershipList.setMembership(membership, membership);
        UniqueMembershipList expectedUniqueMembershipList = new UniqueMembershipList();
        expectedUniqueMembershipList.add(membership);
        assertEquals(expectedUniqueMembershipList, uniqueMembershipList);
    }

    @Test
    public void setMembership_editedMembershipHasDifferentIdentity_success() {
        uniqueMembershipList.add(membership);
        Membership editedMembership = new MembershipBuilder().withClub("Another Club").build();
        uniqueMembershipList.setMembership(membership, editedMembership);
        UniqueMembershipList expectedUniqueMembershipList = new UniqueMembershipList();
        expectedUniqueMembershipList.add(editedMembership);
        assertEquals(expectedUniqueMembershipList, uniqueMembershipList);
    }

    @Test
    public void setMembership_editedMembershipHasNonUniqueIdentity_throwsDuplicateMembershipException() {
        uniqueMembershipList.add(membership);
        Membership membership2 = new MembershipBuilder().withPerson("Another Person").build();
        uniqueMembershipList.add(membership2);
        assertThrows(DuplicateMembershipException.class, ()
                -> uniqueMembershipList.setMembership(membership, membership2));
    }

    @Test
    public void remove_nullMembership_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMembershipList.remove(null));
    }

    @Test
    public void remove_membershipDoesNotExist_throwsMembershipNotFoundException() {
        assertThrows(MembershipNotFoundException.class, () -> uniqueMembershipList.remove(membership));
    }

    @Test
    public void remove_existingMembership_removesMembership() {
        uniqueMembershipList.add(membership);
        uniqueMembershipList.remove(membership);
        UniqueMembershipList expectedUniqueMembershipList = new UniqueMembershipList();
        assertEquals(expectedUniqueMembershipList, uniqueMembershipList);
    }

    @Test
    public void setMemberships_nullUniqueMembershipList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> uniqueMembershipList.setMemberships((UniqueMembershipList) null));
    }

    @Test
    public void setMemberships_uniqueMembershipList_replacesOwnListWithProvidedUniqueMembershipList() {
        uniqueMembershipList.add(membership);
        UniqueMembershipList expectedUniqueMembershipList = new UniqueMembershipList();
        Membership newMembership = new MembershipBuilder().withPerson("Another Person").build();
        expectedUniqueMembershipList.add(newMembership);
        uniqueMembershipList.setMemberships(expectedUniqueMembershipList);
        assertEquals(expectedUniqueMembershipList, uniqueMembershipList);
    }

    @Test
    public void setMemberships_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMembershipList.setMemberships((List<Membership>) null));
    }

    @Test
    public void setMemberships_list_replacesOwnListWithProvidedList() {
        uniqueMembershipList.add(membership);
        Membership newMembership = new MembershipBuilder().withPerson("Another Person").build();
        List<Membership> membershipList = Collections.singletonList(newMembership);
        uniqueMembershipList.setMemberships(membershipList);
        UniqueMembershipList expectedUniqueMembershipList = new UniqueMembershipList();
        expectedUniqueMembershipList.add(newMembership);
        assertEquals(expectedUniqueMembershipList, uniqueMembershipList);
    }

    @Test
    public void setMemberships_listWithDuplicateMemberships_throwsDuplicateMembershipException() {
        List<Membership> listWithDuplicateMemberships = Arrays.asList(membership, membership);
        assertThrows(DuplicateMembershipException.class, ()
                -> uniqueMembershipList.setMemberships(listWithDuplicateMemberships));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueMembershipList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueMembershipList.asUnmodifiableObservableList().toString(), uniqueMembershipList.toString());
    }
}
