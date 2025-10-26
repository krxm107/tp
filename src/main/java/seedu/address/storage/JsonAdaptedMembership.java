package seedu.address.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.membership.Membership;
import seedu.address.model.membership.MembershipEvent;
import seedu.address.model.membership.MembershipStatus;

/**
 * Jackson-friendly version of {@link Membership}.
 * This class stores IDs instead of full objects to prevent circular references.
 */
class JsonAdaptedMembership {

    // Assuming a Person is uniquely identified by their Email, and a Club by its Name.
    private final String personEmail;
    private final String clubName;
    private final String joinDate;
    private final String expiryDate;
    private final List<JsonAdaptedMembershipEvent> membershipEventHistory = new ArrayList<>();
    private final String status;

    @JsonCreator
    public JsonAdaptedMembership(@JsonProperty("personEmail") String personEmail,
                                 @JsonProperty("clubName") String clubName,
                                 @JsonProperty("joinDate") String joinDate,
                                 @JsonProperty("expiryDate") String expiryDate,
                                 @JsonProperty("membershipEventHistory")
                                     List<JsonAdaptedMembershipEvent> membershipEventHistory,
                                 @JsonProperty("status") String status) {
        this.personEmail = personEmail;
        this.clubName = clubName;
        this.joinDate = joinDate;
        this.expiryDate = expiryDate;
        if (membershipEventHistory != null) {
            this.membershipEventHistory.addAll(membershipEventHistory);
        }
        this.status = status;
    }

    /**
     * Converts a given {@code Membership} into this class for Jackson use.
     */
    public JsonAdaptedMembership(Membership source) {
        // Adapt the model objects to their simple string IDs.
        personEmail = source.getPerson().getEmail().value; // Or another unique identifier
        clubName = source.getClub().getName().fullName;
        joinDate = source.getJoinDate().toString();
        expiryDate = source.getExpiryDate().toString();

        membershipEventHistory.addAll(source.getMembershipEventHistory().stream()
                .map(JsonAdaptedMembershipEvent::new)
                .toList());
        status = source.getStatus().toString();
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

    public LocalDate getExpiryDate() {
        return LocalDate.parse(expiryDate);
    }

    public MembershipStatus getStatus() {
        return MembershipStatus.valueOf(status);
    }

    public List<MembershipEvent> getMembershipEventHistory() throws IllegalValueException {
        return membershipEventHistory.stream()
                .map(JsonAdaptedMembershipEvent::toModelType)
                .collect(Collectors.toList());
    }
}
