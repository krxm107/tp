package seedu.address.model.membership;

import java.time.LocalDate;

/**
 * Represents a single, immutable transaction in a membership's lifecycle.
 */
public class MembershipEvent {

    private final EventType type;
    private final LocalDate eventDate;
    private final int monthsAdded; // Duration added
    private final LocalDate newExpiryDate; // The resulting expiry date after this event

    /**
     * Constructor for MembershipEvent.
     * @param type
     * @param eventDate
     * @param monthsAdded
     * @param newExpiryDate
     */
    public MembershipEvent(EventType type, LocalDate eventDate, int monthsAdded, LocalDate newExpiryDate) {
        this.type = type;
        this.eventDate = eventDate;
        this.monthsAdded = monthsAdded;
        this.newExpiryDate = newExpiryDate;
    }

    public EventType getType() {
        return type;
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
                type, eventDate, monthsAdded, newExpiryDate);
    }
}
