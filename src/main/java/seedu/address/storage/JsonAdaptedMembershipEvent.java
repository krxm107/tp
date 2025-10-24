package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

class JsonAdaptedMembershipEvent {

    private final String localDateString;

    @JsonCreator
    public JsonAdaptedMembershipEvent(String localDateName) {
        this.localDateString = localDateName;
    }

    public JsonAdaptedMembershipEvent(LocalDate source) {
        localDateString = source.toString();
    }

    @JsonValue
    public String getLocalDateName() {
        return localDateString;
    }

    // todo: handle invalid date format DateTimeParseException or IllegalValueException
    public LocalDate toModelType() {
        return LocalDate.parse(localDateString);
    }
}
