package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class DistinctPersonListTest {

    private final DistinctPersonList distinctPersonList = new DistinctPersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctPersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(distinctPersonList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        distinctPersonList.add(ALICE);
        assertTrue(distinctPersonList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        distinctPersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(distinctPersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctPersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        distinctPersonList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> distinctPersonList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctPersonList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctPersonList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> distinctPersonList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        distinctPersonList.add(ALICE);
        distinctPersonList.setPerson(ALICE, ALICE);
        DistinctPersonList expectedDistinctPersonList = new DistinctPersonList();
        expectedDistinctPersonList.add(ALICE);
        assertEquals(expectedDistinctPersonList, distinctPersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        distinctPersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        distinctPersonList.setPerson(ALICE, editedAlice);
        DistinctPersonList expectedDistinctPersonList = new DistinctPersonList();
        expectedDistinctPersonList.add(editedAlice);
        assertEquals(expectedDistinctPersonList, distinctPersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        distinctPersonList.add(ALICE);
        distinctPersonList.setPerson(ALICE, BOB);
        DistinctPersonList expectedDistinctPersonList = new DistinctPersonList();
        expectedDistinctPersonList.add(BOB);
        assertEquals(expectedDistinctPersonList, distinctPersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonDistinctIdentity_throwsDuplicatePersonException() {
        distinctPersonList.add(ALICE);
        distinctPersonList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> distinctPersonList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctPersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> distinctPersonList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        distinctPersonList.add(ALICE);
        distinctPersonList.remove(ALICE);
        DistinctPersonList expectedDistinctPersonList = new DistinctPersonList();
        assertEquals(expectedDistinctPersonList, distinctPersonList);
    }

    @Test
    public void setPersons_nullDistinctPersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctPersonList.setPersons((DistinctPersonList) null));
    }

    @Test
    public void setPersons_distinctPersonList_replacesOwnListWithProvidedDistinctPersonList() {
        distinctPersonList.add(ALICE);
        DistinctPersonList expectedDistinctPersonList = new DistinctPersonList();
        expectedDistinctPersonList.add(BOB);
        distinctPersonList.setPersons(expectedDistinctPersonList);
        assertEquals(expectedDistinctPersonList, distinctPersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctPersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        distinctPersonList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        distinctPersonList.setPersons(personList);
        DistinctPersonList expectedDistinctPersonList = new DistinctPersonList();
        expectedDistinctPersonList.add(BOB);
        assertEquals(expectedDistinctPersonList, distinctPersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> distinctPersonList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> distinctPersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(distinctPersonList.asUnmodifiableObservableList().toString(), distinctPersonList.toString());
    }

    @Test
    public void contains_personWithSameEmailInList_returnsTrue() {
        DistinctPersonList list = new DistinctPersonList();
        Person alice = new PersonBuilder().withName("Alice Pauline").withEmail("alice@example.com").build();
        list.add(alice);

        // Same email, other fields may differ -> considered same person
        Person sameEmail = new PersonBuilder().withName("Alice P.")
                .withEmail("alice@example.com").withPhone("99999999").build();

        assertTrue(list.contains(sameEmail));
    }

    @Test
    public void contains_personWithDifferentEmailInList_returnsFalse() {
        DistinctPersonList list = new DistinctPersonList();
        Person alice = new PersonBuilder().withName("Alice Pauline").withEmail("alice@example.com").build();
        list.add(alice);

        // Same name, different email -> NOT the same person now
        Person sameNameDifferentEmail = new PersonBuilder(alice).withEmail("alice+1@example.com").build();

        assertFalse(list.contains(sameNameDifferentEmail));
    }

    @Test
    public void setPerson_sameEmail_throwsDuplicatePersonException() {
        DistinctPersonList list = new DistinctPersonList();
        Person alice = new PersonBuilder().withName("Alice Pauline").withEmail("alice@example.com").build();
        Person bob = new PersonBuilder().withName("Bob").withEmail("bob@example.com").build();
        list.add(alice);
        list.add(bob);

        // Changing Bob's email to Alice's should trip duplicate
        Person bobEmailClash = new PersonBuilder(bob).withEmail("alice@example.com").build();

        assertThrows(DuplicatePersonException.class, () -> list.setPerson(bob, bobEmailClash));
    }
}
