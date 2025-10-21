package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ART;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditClubCommand.EditClubDescriptor;
import seedu.address.testutil.EditClubDescriptorBuilder;

public class EditClubDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditClubDescriptor descriptorWithSameValues = new EditClubDescriptor(DESC_ART);
        assertTrue(DESC_ART.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ART.equals(DESC_ART));

        // null -> returns false
        assertFalse(DESC_ART.equals(null));

        // different types -> returns false
        assertFalse(DESC_ART.equals(5));

        // different values -> returns false
        assertFalse(DESC_ART.equals(DESC_BOB));

        // different name -> returns false
        EditClubDescriptor editedAmy = new EditClubDescriptorBuilder(DESC_ART).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_ART.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditClubDescriptorBuilder(DESC_ART).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_ART.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditClubDescriptorBuilder(DESC_ART).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_ART.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditClubDescriptorBuilder(DESC_ART).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_ART.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditClubDescriptorBuilder(DESC_ART).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_ART.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditClubDescriptor editClubDescriptor = new EditClubDescriptor();
        String expected = EditClubDescriptor.class.getCanonicalName() + "{name="
                + editClubDescriptor.getName().orElse(null) + ", phone="
                + editClubDescriptor.getPhone().orElse(null) + ", email="
                + editClubDescriptor.getEmail().orElse(null) + ", address="
                + editClubDescriptor.getAddress().orElse(null) + ", tags="
                + editClubDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editClubDescriptor.toString());
    }
}
