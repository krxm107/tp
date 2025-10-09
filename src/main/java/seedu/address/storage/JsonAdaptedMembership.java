package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.membership.Membership;

import java.time.LocalDate;

/**
 * Jackson-friendly version of {@link Membership}.
 * This class stores IDs instead of full objects to prevent circular references.
 */
class JsonAdaptedMembership {

    // Assuming a Person is uniquely identified by their Email, and a Club by its Name.
    private final String personEmail;
    private final String clubName;
    private final String joinDate;
    private final String role;

    @JsonCreator
    public JsonAdaptedMembership(@JsonProperty("personEmail") String personEmail,
                                 @JsonProperty("clubName") String clubName,
                                 @JsonProperty("joinDate") String joinDate,
                                 @JsonProperty("role") String role) {
        this.personEmail = personEmail;
        this.clubName = clubName;
        this.joinDate = joinDate;
        this.role = role;
    }

    /**
     * Converts a given {@code Membership} into this class for Jackson use.
     */
    public JsonAdaptedMembership(Membership source) {
        // Adapt the model objects to their simple string IDs.
        personEmail = source.getPerson().getEmail().value; // Or another unique identifier
        clubName = source.getClub().getName().fullName;
        joinDate = source.getJoinDate().toString();
        role = source.getRole();
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

    public String getRole() {
        return role;
    }
}