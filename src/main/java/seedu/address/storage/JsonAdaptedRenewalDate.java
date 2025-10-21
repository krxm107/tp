package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

class JsonAdaptedRenewalDate {

    private final String localDateString;

    @JsonCreator
    public JsonAdaptedRenewalDate(String localDateName) {
        this.localDateString = localDateName;
    }

    public JsonAdaptedRenewalDate(LocalDate source) {
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
