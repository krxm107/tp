package seedu.address.model.membership;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.club.Club;
import seedu.address.model.person.Person;
import seedu.address.testutil.ClubBuilder;
import seedu.address.testutil.PersonBuilder;

public class MembershipTest {

    private Person person;
    private Club club;
    private Membership membership;

    @BeforeEach
    public void setUp() {
        person = new PersonBuilder().build();
        club = new ClubBuilder().build();
        membership = new Membership(person, club);
    }

    @Test
    public void constructor_withDuration_setsCorrectExpiryDate() {
        int duration = 6;
        Membership newMembership = new Membership(person, club, duration);
        assertEquals(LocalDate.now().plusMonths(duration), newMembership.getExpiryDate());
    }

    @Test
    public void constructor_invalidDuration_throwsIllegalArgumentException() {
        int invalidDuration = Membership.MAXIMUM_MEMBERSHIP_DURATION_IN_MONTHS + 1;
        assertThrows(IllegalArgumentException.class, () -> new Membership(person, club, invalidDuration));
    }

    @Test
    public void isActive_activeMembership_returnsTrue() {
        assertTrue(membership.isActive());
    }

    @Test
    public void isActive_expiredMembership_returnsFalse() {
        // Create a membership that expired yesterday
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Membership expiredMembership = new Membership(person, club, LocalDate.now().minusMonths(1), yesterday,
                new ArrayList<>(), MembershipStatus.EXPIRED);
        assertFalse(expiredMembership.isActive());
    }

    @Test
    public void renew_validDuration_renewsMembership() {
        int renewalDuration = 3;
        LocalDate expectedExpiry = membership.getExpiryDate().plusMonths(renewalDuration);
        membership.renew(renewalDuration);
        assertEquals(expectedExpiry, membership.getExpiryDate());
        assertEquals(MembershipStatus.ACTIVE, membership.getStatus());
    }

    @Test
    public void renew_invalidDuration_throwsIllegalArgumentException() {
        int invalidDuration = Membership.MAXIMUM_RENEWAL_DURATION_IN_MONTHS + 1;
        assertThrows(IllegalArgumentException.class, () -> membership.renew(invalidDuration));
    }

    @Test
    public void cancel_activeMembership_pendingCancellation() {
        membership.cancel();
        assertEquals(MembershipStatus.PENDING_CANCELLATION, membership.getStatus());
    }

    @Test
    public void reactivate_expiredMembership_reactivatesMembership() {
        // Create a membership that expired yesterday
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Membership expiredMembership = new Membership(person, club, LocalDate.now().minusMonths(1), yesterday,
                new ArrayList<>(), MembershipStatus.EXPIRED);

        int reactivationDuration = 12;
        expiredMembership.reactivate(reactivationDuration);
        assertEquals(MembershipStatus.ACTIVE, expiredMembership.getStatus());
        // Since it expired, the new expiry date should be from today
        assertEquals(LocalDate.now().plusMonths(reactivationDuration), expiredMembership.getExpiryDate());
    }

    @Test
    public void equals_samePersonAndClub_returnsTrue() {
        Membership sameMembership = new Membership(person, club);
        assertTrue(membership.equals(sameMembership));
    }

    @Test
    public void equals_differentPersonOrClub_returnsFalse() {
        Person anotherPerson = new PersonBuilder().withName("Jane Doe").build();
        Club anotherClub = new ClubBuilder().withName("Another Club").build();
        Membership differentPersonMembership = new Membership(anotherPerson, club);
        Membership differentClubMembership = new Membership(person, anotherClub);
        assertFalse(membership.equals(differentPersonMembership));
        assertFalse(membership.equals(differentClubMembership));
    }
}
