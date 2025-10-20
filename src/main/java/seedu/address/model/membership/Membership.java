package seedu.address.model.membership;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private final Person person;
    private final Club club;
    private final LocalDate joinDate;
    private LocalDate expiryDate;
    private List<LocalDate> renewalHistory;
    private MembershipStatus status;

    /**
     * Constructor with duration specified.
     */
    public Membership(Person person, Club club, int membershipDurationInMonths) {
        requireAllNonNull(person, club, membershipDurationInMonths);

        if (membershipDurationInMonths < MINIMUM_MEMBERSHIP_DURATION_IN_MONTHS
                || membershipDurationInMonths > MAXIMUM_MEMBERSHIP_DURATION_IN_MONTHS) {
            throw new IllegalArgumentException("Membership duration must be between "
                    + MINIMUM_MEMBERSHIP_DURATION_IN_MONTHS + " and " + MAXIMUM_MEMBERSHIP_DURATION_IN_MONTHS
                    + " months.");
        }

        this.person = person;
        this.club = club;
        this.joinDate = LocalDate.now();
        this.expiryDate = joinDate.plusMonths(membershipDurationInMonths);
        this.renewalHistory = new ArrayList<>();
        this.status = MembershipStatus.ACTIVE;
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
        this.expiryDate = expiryDate;
        this.renewalHistory = renewalHistory;
        this.status = status;
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
        this.expiryDate = joinDate.plusMonths(DEFAULT_DURATION_IN_MONTHS); // Default duration of 12 months
        this.renewalHistory = new ArrayList<>();
        this.status = MembershipStatus.ACTIVE;
    }

    /**
     * Checks if the membership is currently active.
     * An active status and a current date before the expiry date are required.
     * @return true if the membership is active, false otherwise.
     */
    public boolean isActive() {
        return this.status == MembershipStatus.ACTIVE && LocalDate.now().isBefore(expiryDate);
    }

    /**
     * Updates the status of the membership based on the current date.
     * This should be called periodically (e.g., daily) by a managing process.
     */
    public void updateStatus() {
        if (this.status == MembershipStatus.ACTIVE && LocalDate.now().isAfter(expiryDate)) {
            this.status = MembershipStatus.EXPIRED;
            System.out.println("Membership for " + person.getName() + " has expired.");
        }
    }

    /**
     * Renews the membership. The behavior depends on the current status.
     * @param renewalDurationInMonths The number of months to extend the membership by.
     */
    public void renew(int renewalDurationInMonths) {
        if (renewalDurationInMonths < MINIMUM_RENEWAL_DURATION_IN_MONTHS
                || renewalDurationInMonths > MAXIMUM_RENEWAL_DURATION_IN_MONTHS) {
            throw new IllegalArgumentException("Renewal duration must be between "
                    + MINIMUM_RENEWAL_DURATION_IN_MONTHS + " and " + MAXIMUM_RENEWAL_DURATION_IN_MONTHS
                    + " months.");
        }

        if (this.status == MembershipStatus.CANCELLED) {
            System.out.println("Cannot renew a cancelled membership. Please create a new one.");
            return;
        }
    }

    /**
     * Reactivates an expired membership by treating it as a new subscription.
     * The join date is kept for historical purposes, but the new period starts from today.
     * @param durationInMonths The duration of the new membership period.
     */
    public void reactivate(int durationInMonths) {
        if (this.status != MembershipStatus.EXPIRED) {
            System.out.println("Only expired memberships can be reactivated.");
            return;
        }
        // The new period starts now
        this.expiryDate = LocalDate.now().plusMonths(durationInMonths);
        this.renewalHistory.add(LocalDate.now());
        this.status = MembershipStatus.ACTIVE;
        System.out.println("Membership for " + person.getName() + " reactivated. New expiry date: " + this.expiryDate);
    }

    /**
     * Cancels the membership. This is a final state.
     */
    public void cancel() {
        this.status = MembershipStatus.CANCELLED;
        System.out.println("Membership for " + person.getName() + " has been cancelled.");
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
        return expiryDate;
    }

    public List<LocalDate> getRenewalHistory() {
        return renewalHistory;
    }

    public MembershipStatus getStatus() {
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
