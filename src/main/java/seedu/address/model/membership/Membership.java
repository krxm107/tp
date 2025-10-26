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
    private ObjectProperty<MembershipStatus> status = new SimpleObjectProperty<>();
    private List<MembershipEvent> membershipEventHistory;

    /**
     * Constructor with duration specified.
     */
    public Membership(Person person, Club club, int initialDurationInMonths) {
        requireAllNonNull(person, club, initialDurationInMonths);

        if (!isValidMembershipDuration(initialDurationInMonths)) {
            throw new IllegalArgumentException("Membership duration must be between "
                    + MINIMUM_MEMBERSHIP_DURATION_IN_MONTHS + " and " + MAXIMUM_MEMBERSHIP_DURATION_IN_MONTHS
                    + " months.");
        }

        this.person = person;
        this.club = club;
        LocalDate today = LocalDate.now();
        this.joinDate = today;
        this.expiryDate.set(joinDate.plusMonths(initialDurationInMonths));
        this.membershipEventHistory = new ArrayList<>();

        // The first event is always a JOIN event. This captures the initial duration.
        MembershipEvent joinEvent = new MembershipEvent(
                EventType.JOIN,
                today,
                initialDurationInMonths,
                this.expiryDate.get()
        );
        this.membershipEventHistory.add(joinEvent);

        this.status.set(MembershipStatus.ACTIVE);
    }

    /**
     * Constructor with all fields specified. Use to recreate from storage.
     */
    public Membership(Person person, Club club, LocalDate joinDate, LocalDate expiryDate,
                      List<MembershipEvent> eventHistory, MembershipStatus status) {
        requireAllNonNull(person, club, joinDate, expiryDate, eventHistory, status);

        this.person = person;
        this.club = club;
        this.joinDate = joinDate;
        this.expiryDate.set(expiryDate);
        this.membershipEventHistory = eventHistory;
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
        this.membershipEventHistory = new ArrayList<>();
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
        return getStatus() == MembershipStatus.ACTIVE && LocalDate.now().isBefore(expiryDate.get());
    }

    /**
     * Updates the status of the membership based on the current date.
     * This should be called everytime we start the app.
     */
    public void updateStatus() {
        if (getStatus() == MembershipStatus.ACTIVE && LocalDate.now().isAfter(expiryDate.get())) {
            this.status.set(MembershipStatus.EXPIRED);
            logger.info("Membership for " + person.getName() + " has expired.");
        } else if (getStatus() == MembershipStatus.PENDING_CANCELLATION
                && LocalDate.now().isAfter(expiryDate.get())) {
            this.status.set(MembershipStatus.CANCELLED);
            logger.info("Membership for " + person.getName() + " has been cancelled after pending cancellation.");
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
        if (getStatus() == MembershipStatus.CANCELLED) {
            throw new IllegalArgumentException("Membership is cancelled. Please reactivate instead.");
        } else if (getStatus() == MembershipStatus.EXPIRED) {
            throw new IllegalArgumentException("Membership has expired. Please reactivate instead.");
        }

        LocalDate today = LocalDate.now();
        EventType eventType;
        LocalDate newExpiry;
        eventType = EventType.RENEW;
        // Extends the existing expiry date
        newExpiry = getExpiryDate().plusMonths(durationInMonths);
        this.expiryDate.set(newExpiry);

        // Create renew event record
        MembershipEvent renewalEvent = new MembershipEvent(
                eventType,
                today,
                durationInMonths,
                newExpiry
        );
        this.membershipEventHistory.add(renewalEvent);
        this.status.set(MembershipStatus.ACTIVE);
        logger.info("Membership for " + person.getName() + " renewed. New expiry date: " + this.expiryDate);
    }

    /**
     * Cancels the membership.
     */
    public void cancel() {
        if (getStatus() == MembershipStatus.CANCELLED || getStatus() == MembershipStatus.PENDING_CANCELLATION) {
            throw new IllegalArgumentException("Membership is already cancelled.");
        }

        LocalDate today = LocalDate.now();
        LocalDate expiry = this.expiryDate.get();
        // Check if expiry date is in the past
        if (today.isAfter(expiry)) {
            this.status.set(MembershipStatus.CANCELLED);
            logger.info("Membership for " + person.getName() + " has been cancelled.");
        } else {
            this.status.set(MembershipStatus.PENDING_CANCELLATION);
            logger.info("Membership for " + person.getName() + " is pending cancellation until expiry date: "
                    + expiry);
        }
        EventType eventType = EventType.CANCEL;
        int durationInMonths = 0;

        // Create cancel event record
        MembershipEvent cancelEvent = new MembershipEvent(
                eventType,
                today,
                durationInMonths,
                expiry
        );
        this.membershipEventHistory.add(cancelEvent);
    }

    /**
     * Reactivates an expired or cancelled membership.
     * @param durationInMonths The duration in months for the reactivated membership.
     */
    public void reactivate(int durationInMonths) {
        if (getStatus() == MembershipStatus.ACTIVE) {
            throw new IllegalArgumentException("Only expired or cancelled memberships can be reactivated.");
        }
        if (!isValidMembershipDuration(durationInMonths)) {
            throw new IllegalArgumentException("Membership duration must be between "
                    + MINIMUM_MEMBERSHIP_DURATION_IN_MONTHS + " and " + MAXIMUM_MEMBERSHIP_DURATION_IN_MONTHS
                    + " months.");
        }

        LocalDate today = LocalDate.now();
        LocalDate newExpiry;
        if (today.isAfter(expiryDate.get())) {
            // If previously expired, start new period from today
            logger.info("Expiry date was in the past, setting new expiry date from today.");
            newExpiry = today.plusMonths(durationInMonths);
        } else {
            // If not expired, extend from current expiry date
            logger.info("Expiry date was in the future, extending from current expiry date.");
            newExpiry = getExpiryDate().plusMonths(durationInMonths);
        }
        this.expiryDate.set(newExpiry);

        EventType eventType;
        eventType = EventType.REACTIVATE;

        // Create reactivate event record
        MembershipEvent reactivateEvent = new MembershipEvent(
                eventType,
                today,
                durationInMonths,
                newExpiry
        );
        this.membershipEventHistory.add(reactivateEvent);
        this.status.set(MembershipStatus.ACTIVE);
        logger.info("Membership for " + person.getName() + " reactivated. New expiry date: " + this.expiryDate);
    }

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

    public List<MembershipEvent> getMembershipEventHistory() {
        return membershipEventHistory;
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
