package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.model.membership.EventType;
import seedu.address.model.membership.MembershipEvent;

class JsonAdaptedMembershipEvent {

    private final String eventType;
    private final String eventDate;
    private final String monthsAdded; // Duration added
    private final String newExpiryDate; // The resulting expiry date after this event

    @JsonCreator
    public JsonAdaptedMembershipEvent(@JsonProperty("eventType") String eventType,
                                      @JsonProperty("eventDate") String eventDate,
                                      @JsonProperty("monthsAdded") String monthsAdded,
                                      @JsonProperty("newExpiryDate") String newExpiryDate) {
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.monthsAdded = monthsAdded;
        this.newExpiryDate = newExpiryDate;
    }

    public JsonAdaptedMembershipEvent(MembershipEvent source) {
        this.eventType = source.getEventType().toString();
        this.eventDate = source.getEventDate().toString();
        this.monthsAdded = Integer.toString(source.getMonthsAdded());
        this.newExpiryDate = source.getNewExpiryDate().toString();
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
