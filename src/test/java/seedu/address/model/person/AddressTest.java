package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.field.Address;

public class AddressTest {

    @Test
    public void isValidAddress() {
        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-")); // one character

        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; "
                + "San Francisco CA 2349879; USA")); // long address with semicolons
    }

    @Test
    public void equals() {
        Address address = new Address("Valid Address");

        // same values -> returns true
        assertTrue(address.equals(new Address("Valid Address")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("Other Valid Address")));
    }

    /**
     * Unit tests for {@link Address}.
     * Includes validation and optional (empty) phone behavior.
     */
    @Test
    void emptyAddressAllowedAndDisplaysEmpty() {
        Address p = new Address("");
        assertEquals("", p.toString());
        assertFalse(p.isPresent());
    }
}
