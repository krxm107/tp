package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.membership.Membership;
import seedu.address.model.membership.MembershipStatus;

import java.util.function.Predicate;

public class MembershipStatusParser {

    public Predicate<Membership> parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();
        if (trimmedArgs.isEmpty()) {
            return membership -> !membership.getStatus().equals(MembershipStatus.EXPIRED);
        }
        if (args.contains("*")) {
            return membership -> true;
        }

        Predicate<Membership> predicate = membership -> false;
        if (args.contains("a")) {
            predicate = predicate.or(membership -> membership.getStatus().equals(
                    MembershipStatus.ACTIVE));
        }
        if (args.contains("c")) {
            predicate = predicate.or(membership -> membership.getStatus().equals(
                    MembershipStatus.CANCELLED));
        }
        if (args.contains("e")) {
            predicate = predicate.or(membership -> membership.getStatus().equals(
                    MembershipStatus.EXPIRED));
        }
        if (args.contains("p")) {
            predicate = predicate.or(membership -> membership.getStatus().equals(
                    MembershipStatus.PENDING_CANCELLATION));
        }
        return predicate;
    }

}
