package seedu.address.model.membership;

import java.time.LocalDate;

/**
 * Represents a single, immutable transaction in a membership's lifecycle.
 */
public class MembershipEvent {

    private final EventType eventType;
    private final LocalDate eventDate;
    private final int monthsAdded; // Duration added
    private final LocalDate newExpiryDate; // The resulting expiry date after this event

    /**
     * Constructor for MembershipEvent.
     * @param eventType
     * @param eventDate
     * @param monthsAdded
     * @param newExpiryDate
     */
    public MembershipEvent(EventType eventType, LocalDate eventDate, int monthsAdded, LocalDate newExpiryDate) {
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.monthsAdded = monthsAdded;
        this.newExpiryDate = newExpiryDate;
    }

    public EventType getEventType() {
        return eventType;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public int getMonthsAdded() {
        return monthsAdded;
    }

    public LocalDate getNewExpiryDate() {
        return newExpiryDate;
    }

    @Override
    public String toString() {
        return String.format("[%s] on %s: Added %d months. New Expiry: %s",
                eventType, eventDate, monthsAdded, newExpiryDate);
    }
}
