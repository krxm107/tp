package seedu.address.model.membership;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.club.Club;
import seedu.address.model.person.Person;

/**
 * Represents a Membership of a Person in a Club.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Membership {

    public static final int DEFAULT_DURATION_IN_MONTHS = 12;
    public static final int MINIMUM_RENEWAL_DURATION_IN_MONTHS = 1;
    public static final int MAXIMUM_RENEWAL_DURATION_IN_MONTHS = 24;
    public static final int MINIMUM_MEMBERSHIP_DURATION_IN_MONTHS = 1;
    public static final int MAXIMUM_MEMBERSHIP_DURATION_IN_MONTHS = 24;

    private static final Logger logger = LogsCenter.getLogger(Membership.class);

    private final Person person;
    private final Club club;
    private final LocalDate joinDate;
    private ObjectProperty<LocalDate> expiryDate = new SimpleObjectProperty<>();
    private List<LocalDate> renewalHistory;
    private ObjectProperty<MembershipStatus> status = new SimpleObjectProperty<>();

    /**
     * Constructor with duration specified.
     */
    public Membership(Person person, Club club, int durationInMonths) {
        requireAllNonNull(person, club, durationInMonths);

        if (!isValidMembershipDuration(durationInMonths)) {
            throw new IllegalArgumentException("Membership duration must be between "
                    + MINIMUM_MEMBERSHIP_DURATION_IN_MONTHS + " and " + MAXIMUM_MEMBERSHIP_DURATION_IN_MONTHS
                    + " months.");
        }

        this.person = person;
        this.club = club;
        this.joinDate = LocalDate.now();
        this.expiryDate.set(joinDate.plusMonths(durationInMonths));
        this.renewalHistory = new ArrayList<>();
        this.status.set(MembershipStatus.ACTIVE);
    }

    /**
     * Constructor with all fields specified. Use to recreate from storage.
     */
    public Membership(Person person, Club club, LocalDate joinDate, LocalDate expiryDate,
                      List<LocalDate> renewalHistory, MembershipStatus status) {
        requireAllNonNull(person, club, joinDate, expiryDate, renewalHistory, status);

        this.person = person;
        this.club = club;
        this.joinDate = joinDate;
        this.expiryDate.set(expiryDate);
        this.renewalHistory = renewalHistory;
        this.status.set(status);
    }

    /**
     * Constructor that sets joinDate to current date and expiryDate to 12 months later.
     */
    public Membership(Person person, Club club) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(club);
        this.person = person;
        this.club = club;
        this.joinDate = LocalDate.now();
        this.expiryDate.set(joinDate.plusMonths(DEFAULT_DURATION_IN_MONTHS)); // Default duration of 12 months
        this.renewalHistory = new ArrayList<>();
        this.status.set(MembershipStatus.ACTIVE);
    }

    private boolean isValidMembershipDuration(int durationInMonths) {
        return durationInMonths >= MINIMUM_MEMBERSHIP_DURATION_IN_MONTHS
                && durationInMonths <= MAXIMUM_MEMBERSHIP_DURATION_IN_MONTHS;
    }

    private boolean isValidRenewalDuration(int durationInMonths) {
        return durationInMonths >= MINIMUM_RENEWAL_DURATION_IN_MONTHS
                && durationInMonths <= MAXIMUM_RENEWAL_DURATION_IN_MONTHS;
    }

    /**
     * Checks if the membership is currently active.
     * An active status and a current date before the expiry date are required.
     * @return true if the membership is active, false otherwise.
     */
    public boolean isActive() {
        return this.status.get() == MembershipStatus.ACTIVE && LocalDate.now().isBefore(expiryDate.get());
    }

    /**
     * Updates the status of the membership based on the current date.
     * This should be called everytime we start the app.
     */
    public void updateStatus() {
        if (this.status.get() == MembershipStatus.ACTIVE && LocalDate.now().isAfter(expiryDate.get())) {
            this.status.set(MembershipStatus.EXPIRED);
            logger.info("Membership for " + person.getName() + " has expired.");
        }
    }

    /**
     * Renews the membership. The behavior depends on the current status.
     * @param durationInMonths The number of months to extend the membership by.
     */
    public void renew(int durationInMonths) {
        if (!isValidRenewalDuration(durationInMonths)) {
            throw new IllegalArgumentException("Renewal duration must be between "
                    + MINIMUM_RENEWAL_DURATION_IN_MONTHS + " and " + MAXIMUM_RENEWAL_DURATION_IN_MONTHS
                    + " months.");
        }

        if (this.status.get() == MembershipStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot renew a cancelled membership. Please create a new one.");
        }

        if (this.status.get() == MembershipStatus.EXPIRED) {
            // If expired, start new period from today
            this.expiryDate.set(LocalDate.now().plusMonths(durationInMonths));
        } else {
            // If active, extend from current expiry date
            this.expiryDate.set(this.expiryDate.get().plusMonths(durationInMonths));
        }
        this.renewalHistory.add(LocalDate.now());
        this.status.set(MembershipStatus.ACTIVE);
        logger.info("Membership for " + person.getName() + " renewed. New expiry date: " + this.expiryDate);
    }

    /**
     * Cancels the membership. This is a final state.
     */
    public void cancel() {
        this.status.set(MembershipStatus.CANCELLED);
        logger.info("Membership for " + person.getName() + " has been cancelled.");
    }

    /**
     * Reactivates a cancelled membership.
     * @param durationInMonths The duration in months for the reactivated membership.
     */
    public void reactivate(int durationInMonths) {
        if (this.status.get() != MembershipStatus.CANCELLED) {
            throw new IllegalArgumentException("Only cancelled memberships can be reactivated.");
        }
        if (!isValidMembershipDuration(durationInMonths)) {
            throw new IllegalArgumentException("Membership duration must be between "
                    + MINIMUM_MEMBERSHIP_DURATION_IN_MONTHS + " and " + MAXIMUM_MEMBERSHIP_DURATION_IN_MONTHS
                    + " months.");
        }
        this.status.set(MembershipStatus.ACTIVE);
        if (LocalDate.now().isAfter(expiryDate.get())) {
            // If previously expired, start new period from today
            logger.info("Expiry date was in the past, setting new expiry date from today.");
            this.expiryDate.set(LocalDate.now().plusMonths(durationInMonths));
        } else {
            // If not expired, extend from current expiry date
            logger.info("Expiry date was in the future, extending from current expiry date.");
            LocalDate newExpiryDate = this.expiryDate.get().plusMonths(durationInMonths);
            this.expiryDate.set(newExpiryDate);
        }
        logger.info("Membership for " + person.getName() + " reactivated. New expiry date: " + this.expiryDate);
    }

    // todo: implement isValidLocalDate later
    public Person getPerson() {
        return person;
    }

    public Club getClub() {
        return club;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate.get();
    }

    public List<LocalDate> getRenewalHistory() {
        return renewalHistory;
    }

    public MembershipStatus getStatus() {
        return status.get();
    }

    public ObjectProperty<LocalDate> expiryDateProperty() {
        return expiryDate;
    }

    public ObjectProperty<MembershipStatus> statusProperty() {
        return status;
    }

    public String getClubName() {
        return club.getName().toString();
    }

    public String getPersonName() {
        return person.getName().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Membership that = (Membership) o;
        return person.equals(that.person)
                && club.equals(that.club);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, club);
    }
}
