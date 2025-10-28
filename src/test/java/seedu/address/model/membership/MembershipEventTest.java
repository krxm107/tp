package seedu.address.model.membership;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class MembershipEventTest {

    private final EventType eventType = EventType.JOIN;
    private final LocalDate eventDate = LocalDate.of(2023, 1, 1);
    private final int monthsAdded = 12;
    private final LocalDate newExpiryDate = LocalDate.of(2024, 1, 1);
    private final MembershipEvent event = new MembershipEvent(eventType, eventDate, monthsAdded, newExpiryDate);

    @Test
    public void constructor_createsEventCorrectly() {
        assertEquals(eventType, event.getEventType());
        assertEquals(eventDate, event.getEventDate());
        assertEquals(monthsAdded, event.getMonthsAdded());
        assertEquals(newExpiryDate, event.getNewExpiryDate());
    }

    @Test
    public void getters_workCorrectly() {
        assertEquals(eventType, event.getEventType());
        assertEquals(eventDate, event.getEventDate());
        assertEquals(monthsAdded, event.getMonthsAdded());
        assertEquals(newExpiryDate, event.getNewExpiryDate());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(event.equals(event));

        // same values -> returns true
        MembershipEvent eventCopy = new MembershipEvent(eventType, eventDate, monthsAdded, newExpiryDate);
        assertTrue(event.equals(eventCopy));

        // different types -> returns false
        assertFalse(event.equals(1));

        // null -> returns false
        assertFalse(event.equals(null));

        // different event type -> returns false
        MembershipEvent differentEventType =
                new MembershipEvent(EventType.RENEW, eventDate, monthsAdded, newExpiryDate);
        assertFalse(event.equals(differentEventType));

        // different event date -> returns false
        MembershipEvent differentEventDate =
                new MembershipEvent(eventType, eventDate.plusDays(1), monthsAdded, newExpiryDate);
        assertFalse(event.equals(differentEventDate));

        // different months added -> returns false
        MembershipEvent differentMonthsAdded =
                new MembershipEvent(eventType, eventDate, monthsAdded + 1, newExpiryDate);
        assertFalse(event.equals(differentMonthsAdded));

        // different new expiry date -> returns false
        MembershipEvent differentNewExpiryDate =
                new MembershipEvent(eventType, eventDate, monthsAdded, newExpiryDate.plusDays(1));
        assertFalse(event.equals(differentNewExpiryDate));
    }

    @Test
    public void hashCode_consistency() {
        MembershipEvent eventCopy = new MembershipEvent(eventType, eventDate, monthsAdded, newExpiryDate);
        assertEquals(event.hashCode(), eventCopy.hashCode());

        MembershipEvent differentEvent = new MembershipEvent(EventType.CANCEL, eventDate, monthsAdded, newExpiryDate);
        assertNotEquals(event.hashCode(), differentEvent.hashCode());
    }

    @Test
    public void toString_correctFormat() {
        String expectedString = String.format("[%s] on %s: Added %d months. New Expiry: %s",
                eventType, eventDate, monthsAdded, newExpiryDate);
        assertEquals(expectedString, event.toString());
    }
}
