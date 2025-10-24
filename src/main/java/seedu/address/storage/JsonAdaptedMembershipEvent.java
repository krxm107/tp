package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.model.membership.EventType;
import seedu.address.model.membership.MembershipEvent;

class JsonAdaptedMembershipEvent {

    private final String eventType;
    private final String eventDate;
    private final String monthsAdded; // Duration added
    private final String newExpiryDate; // The resulting expiry date after this event

    @JsonCreator
    public JsonAdaptedMembershipEvent(MembershipEvent membershipEvent) {
        this.eventType = membershipEvent.getEventType().toString();
        this.eventDate = membershipEvent.getEventDate().toString();
        this.monthsAdded = Integer.toString(membershipEvent.getMonthsAdded());
        this.newExpiryDate = membershipEvent.getNewExpiryDate().toString();
    }

    @JsonValue
    public MembershipEvent toModelType() {
        EventType modelEventType = EventType.valueOf(eventType);
        LocalDate modelEventDate = LocalDate.parse(eventDate);
        int modelMonthsAdded = Integer.parseInt(monthsAdded);
        LocalDate modelNewExpiryDate = LocalDate.parse(newExpiryDate);
        return new MembershipEvent(modelEventType, modelEventDate, modelMonthsAdded, modelNewExpiryDate);
    }
}
