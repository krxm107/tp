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

public class DistinctClubListTest {

    private final DistinctClubList distinctClubList = new DistinctClubList();

    @Test
    public void contains_nullClub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctClubList.contains(null));
    }

    @Test
    public void contains_clubNotInList_returnsFalse() {
        assertFalse(distinctClubList.contains(ARCHERY));
    }

    @Test
    public void contains_clubInList_returnsTrue() {
        distinctClubList.add(ARCHERY);
        assertTrue(distinctClubList.contains(ARCHERY));
    }

    @Test
    public void contains_clubWithSameIdentityFieldsInList_returnsTrue() {
        distinctClubList.add(ARCHERY);
        Club editedArchery = new ClubBuilder(ARCHERY).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(distinctClubList.contains(editedArchery));
    }

    @Test
    public void add_nullClub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctClubList.add(null));
    }

    @Test
    public void add_duplicateClub_throwsDuplicateClubException() {
        distinctClubList.add(ARCHERY);
        assertThrows(DuplicateClubException.class, () -> distinctClubList.add(ARCHERY));
    }

    @Test
    public void setClub_nullTargetClub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctClubList.setClub(null, ARCHERY));
    }

    @Test
    public void setClub_nullEditedClub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctClubList.setClub(ARCHERY, null));
    }

    @Test
    public void setClub_targetClubNotInList_throwsClubNotFoundException() {
        assertThrows(ClubNotFoundException.class, () -> distinctClubList.setClub(ARCHERY, ARCHERY));
    }

    @Test
    public void setClub_editedClubIsSameClub_success() {
        distinctClubList.add(ARCHERY);
        distinctClubList.setClub(ARCHERY, ARCHERY);
        DistinctClubList expectedDistinctClubList = new DistinctClubList();
        expectedDistinctClubList.add(ARCHERY);
        assertEquals(expectedDistinctClubList, distinctClubList);
    }

    @Test
    public void setClub_editedClubHasSameIdentity_success() {
        distinctClubList.add(ARCHERY);
        Club editedArchery = new ClubBuilder(ARCHERY).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        distinctClubList.setClub(ARCHERY, editedArchery);
        DistinctClubList expectedDistinctClubList = new DistinctClubList();
        expectedDistinctClubList.add(editedArchery);
        assertEquals(expectedDistinctClubList, distinctClubList);
    }

    @Test
    public void setClub_editedClubHasDifferentIdentity_success() {
        distinctClubList.add(ARCHERY);
        distinctClubList.setClub(ARCHERY, BALL);
        DistinctClubList expectedDistinctClubList = new DistinctClubList();
        expectedDistinctClubList.add(BALL);
        assertEquals(expectedDistinctClubList, distinctClubList);
    }

    @Test
    public void setClub_editedClubHasNonDistinctIdentity_throwsDuplicateClubException() {
        distinctClubList.add(ARCHERY);
        distinctClubList.add(BALL);
        assertThrows(DuplicateClubException.class, () -> distinctClubList.setClub(ARCHERY, BALL));
    }

    @Test
    public void remove_nullClub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctClubList.remove(null));
    }

    @Test
    public void remove_clubDoesNotExist_throwsClubNotFoundException() {
        assertThrows(ClubNotFoundException.class, () -> distinctClubList.remove(ARCHERY));
    }

    @Test
    public void remove_existingClub_removesClub() {
        distinctClubList.add(ARCHERY);
        distinctClubList.remove(ARCHERY);
        DistinctClubList expectedDistinctClubList = new DistinctClubList();
        assertEquals(expectedDistinctClubList, distinctClubList);
    }

    @Test
    public void setClubs_nullDistinctClubList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctClubList.setClubs((DistinctClubList) null));
    }

    @Test
    public void setClubs_distinctClubList_replacesOwnListWithProvidedDistinctClubList() {
        distinctClubList.add(ARCHERY);
        DistinctClubList expectedDistinctClubList = new DistinctClubList();
        expectedDistinctClubList.add(BALL);
        distinctClubList.setClubs(expectedDistinctClubList);
        assertEquals(expectedDistinctClubList, distinctClubList);
    }

    @Test
    public void setClubs_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctClubList.setClubs((List<Club>) null));
    }

    @Test
    public void setClubs_list_replacesOwnListWithProvidedList() {
        distinctClubList.add(ARCHERY);
        List<Club> clubList = Collections.singletonList(BALL);
        distinctClubList.setClubs(clubList);
        DistinctClubList expectedDistinctClubList = new DistinctClubList();
        expectedDistinctClubList.add(BALL);
        assertEquals(expectedDistinctClubList, distinctClubList);
    }

    @Test
    public void setClubs_listWithDuplicateClubs_throwsDuplicateClubException() {
        List<Club> listWithDuplicateClubs = Arrays.asList(ARCHERY, ARCHERY);
        assertThrows(DuplicateClubException.class, () -> distinctClubList.setClubs(listWithDuplicateClubs));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> distinctClubList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(distinctClubList.asUnmodifiableObservableList().toString(), distinctClubList.toString());
    }
}
