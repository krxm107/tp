package seedu.address.model.club;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClubs.ARCHERY;
import static seedu.address.testutil.TypicalClubs.BALL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.club.exceptions.ClubNotFoundException;
import seedu.address.model.club.exceptions.DuplicateClubException;
import seedu.address.testutil.ClubBuilder;

public class UniqueClubListTest {

    private final UniqueClubList uniqueClubList = new UniqueClubList();

    @Test
    public void contains_nullClub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClubList.contains(null));
    }

    @Test
    public void contains_clubNotInList_returnsFalse() {
        assertFalse(uniqueClubList.contains(ARCHERY));
    }

    @Test
    public void contains_clubInList_returnsTrue() {
        uniqueClubList.add(ARCHERY);
        assertTrue(uniqueClubList.contains(ARCHERY));
    }

    @Test
    public void contains_clubWithSameIdentityFieldsInList_returnsTrue() {
        uniqueClubList.add(ARCHERY);
        Club editedArchery = new ClubBuilder(ARCHERY).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueClubList.contains(editedArchery));
    }

    @Test
    public void add_nullClub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClubList.add(null));
    }

    @Test
    public void add_duplicateClub_throwsDuplicateClubException() {
        uniqueClubList.add(ARCHERY);
        assertThrows(DuplicateClubException.class, () -> uniqueClubList.add(ARCHERY));
    }

    @Test
    public void setClub_nullTargetClub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClubList.setClub(null, ARCHERY));
    }

    @Test
    public void setClub_nullEditedClub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClubList.setClub(ARCHERY, null));
    }

    @Test
    public void setClub_targetClubNotInList_throwsClubNotFoundException() {
        assertThrows(ClubNotFoundException.class, () -> uniqueClubList.setClub(ARCHERY, ARCHERY));
    }

    @Test
    public void setClub_editedClubIsSameClub_success() {
        uniqueClubList.add(ARCHERY);
        uniqueClubList.setClub(ARCHERY, ARCHERY);
        UniqueClubList expectedUniqueClubList = new UniqueClubList();
        expectedUniqueClubList.add(ARCHERY);
        assertEquals(expectedUniqueClubList, uniqueClubList);
    }

    @Test
    public void setClub_editedClubHasSameIdentity_success() {
        uniqueClubList.add(ARCHERY);
        Club editedArchery = new ClubBuilder(ARCHERY).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueClubList.setClub(ARCHERY, editedArchery);
        UniqueClubList expectedUniqueClubList = new UniqueClubList();
        expectedUniqueClubList.add(editedArchery);
        assertEquals(expectedUniqueClubList, uniqueClubList);
    }

    @Test
    public void setClub_editedClubHasDifferentIdentity_success() {
        uniqueClubList.add(ARCHERY);
        uniqueClubList.setClub(ARCHERY, BALL);
        UniqueClubList expectedUniqueClubList = new UniqueClubList();
        expectedUniqueClubList.add(BALL);
        assertEquals(expectedUniqueClubList, uniqueClubList);
    }

    @Test
    public void setClub_editedClubHasNonUniqueIdentity_throwsDuplicateClubException() {
        uniqueClubList.add(ARCHERY);
        uniqueClubList.add(BALL);
        assertThrows(DuplicateClubException.class, () -> uniqueClubList.setClub(ARCHERY, BALL));
    }

    @Test
    public void remove_nullClub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClubList.remove(null));
    }

    @Test
    public void remove_clubDoesNotExist_throwsClubNotFoundException() {
        assertThrows(ClubNotFoundException.class, () -> uniqueClubList.remove(ARCHERY));
    }

    @Test
    public void remove_existingClub_removesClub() {
        uniqueClubList.add(ARCHERY);
        uniqueClubList.remove(ARCHERY);
        UniqueClubList expectedUniqueClubList = new UniqueClubList();
        assertEquals(expectedUniqueClubList, uniqueClubList);
    }

    @Test
    public void setClubs_nullUniqueClubList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClubList.setClubs((UniqueClubList) null));
    }

    @Test
    public void setClubs_uniqueClubList_replacesOwnListWithProvidedUniqueClubList() {
        uniqueClubList.add(ARCHERY);
        UniqueClubList expectedUniqueClubList = new UniqueClubList();
        expectedUniqueClubList.add(BALL);
        uniqueClubList.setClubs(expectedUniqueClubList);
        assertEquals(expectedUniqueClubList, uniqueClubList);
    }

    @Test
    public void setClubs_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClubList.setClubs((List<Club>) null));
    }

    @Test
    public void setClubs_list_replacesOwnListWithProvidedList() {
        uniqueClubList.add(ARCHERY);
        List<Club> clubList = Collections.singletonList(BALL);
        uniqueClubList.setClubs(clubList);
        UniqueClubList expectedUniqueClubList = new UniqueClubList();
        expectedUniqueClubList.add(BALL);
        assertEquals(expectedUniqueClubList, uniqueClubList);
    }

    @Test
    public void setClubs_listWithDuplicateClubs_throwsDuplicateClubException() {
        List<Club> listWithDuplicateClubs = Arrays.asList(ARCHERY, ARCHERY);
        assertThrows(DuplicateClubException.class, () -> uniqueClubList.setClubs(listWithDuplicateClubs));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueClubList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueClubList.asUnmodifiableObservableList().toString(), uniqueClubList.toString());
    }
}
