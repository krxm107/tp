package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.membership.Membership;

/**
 * Jackson-friendly version of {@link Membership}.
 * This class stores IDs instead of full objects to prevent circular references.
 */
class JsonAdaptedMembership {

    // Assuming a Person is uniquely identified by their Email, and a Club by its Name.
    private final String personEmail;
    private final String clubName;
    private final String joinDate;

    @JsonCreator
    public JsonAdaptedMembership(@JsonProperty("personEmail") String personEmail,
                                 @JsonProperty("clubName") String clubName,
                                 @JsonProperty("joinDate") String joinDate) {
        this.personEmail = personEmail;
        this.clubName = clubName;
        this.joinDate = joinDate;
    }

    /**
     * Converts a given {@code Membership} into this class for Jackson use.
     */
    public JsonAdaptedMembership(Membership source) {
        // Adapt the model objects to their simple string IDs.
        personEmail = source.getPerson().getEmail().value; // Or another unique identifier
        clubName = source.getClub().getName().fullName;
        joinDate = source.getJoinDate().toString();
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public String getClubName() {
        return clubName;
    }

    public LocalDate getJoinDate() {
        return LocalDate.parse(joinDate);
    }
}
