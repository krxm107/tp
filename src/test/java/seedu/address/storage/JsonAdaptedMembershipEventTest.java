package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import seedu.address.model.membership.EventType;
import seedu.address.model.membership.MembershipEvent;

public class JsonAdaptedMembershipEventTest {

    private static final String VALID_EVENT_TYPE = "JOIN";
    private static final String VALID_EVENT_DATE = "2023-01-01";
    private static final String VALID_MONTHS_ADDED = "12";
    private static final String VALID_NEW_EXPIRY_DATE = "2024-01-01";

    private static final String INVALID_EVENT_TYPE = "INVALID_TYPE";
    private static final String INVALID_DATE = "2023/01/01"; // Invalid format
    private static final String INVALID_MONTHS = "twelve";

    private static final MembershipEvent TEST_EVENT = new MembershipEvent(
            EventType.valueOf(VALID_EVENT_TYPE),
            LocalDate.parse(VALID_EVENT_DATE),
            Integer.parseInt(VALID_MONTHS_ADDED),
            LocalDate.parse(VALID_NEW_EXPIRY_DATE));

    @Test
    public void toModelType_validEventDetails_returnsMembershipEvent() {
        JsonAdaptedMembershipEvent event = new JsonAdaptedMembershipEvent(TEST_EVENT);
        assertEquals(TEST_EVENT, event.toModelType());
    }

    @Test
    public void toModelType_invalidEventType_throwsIllegalArgumentException() {
        JsonAdaptedMembershipEvent event = new JsonAdaptedMembershipEvent(
                INVALID_EVENT_TYPE, VALID_EVENT_DATE, VALID_MONTHS_ADDED, VALID_NEW_EXPIRY_DATE);
        assertThrows(IllegalArgumentException.class, event::toModelType);
    }

    @Test
    public void toModelType_invalidEventDate_throwsDateTimeParseException() {
        JsonAdaptedMembershipEvent event = new JsonAdaptedMembershipEvent(
                VALID_EVENT_TYPE, INVALID_DATE, VALID_MONTHS_ADDED, VALID_NEW_EXPIRY_DATE);
        assertThrows(DateTimeParseException.class, event::toModelType);
    }

    @Test
    public void toModelType_invalidMonthsAdded_throwsNumberFormatException() {
        JsonAdaptedMembershipEvent event = new JsonAdaptedMembershipEvent(
                VALID_EVENT_TYPE, VALID_EVENT_DATE, INVALID_MONTHS, VALID_NEW_EXPIRY_DATE);
        assertThrows(NumberFormatException.class, event::toModelType);
    }

    @Test
    public void toModelType_invalidNewExpiryDate_throwsDateTimeParseException() {
        JsonAdaptedMembershipEvent event = new JsonAdaptedMembershipEvent(
                VALID_EVENT_TYPE, VALID_EVENT_DATE, VALID_MONTHS_ADDED, INVALID_DATE);
        assertThrows(DateTimeParseException.class, event::toModelType);
    }
}
