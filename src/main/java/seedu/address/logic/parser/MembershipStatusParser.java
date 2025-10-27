package seedu.address.logic.parser;

import java.util.function.Predicate;

import seedu.address.model.membership.Membership;

/**
 * Parses the membership status arguments of a list member or list membership command
 */
public class MembershipStatusParser {

    /**
     * Parses the arguments in the context of a <code>MembershipClubCommand</code> or <code>MembershipPersonCommand</code>
     * and returns a <code>Predicate</code> which specifies the status of <code>Membership</code>s to be shown.
     */
    public Predicate<Membership> parse(String args) {
        String trimmedArgs = args.trim().toLowerCase();
        MembershipStatusPredicate predicate = new MembershipStatusPredicate();

        if (trimmedArgs.isEmpty()) {
            return predicate;
        }

        if (args.contains("a")) {
            predicate.addPredicate("a");
        }
        if (args.contains("c")) {
            predicate.addPredicate("c");
        }
        if (args.contains("e")) {
            predicate.addPredicate("e");
        }
        if (args.contains("p")) {
            predicate.addPredicate("p");
        }
        return predicate;
    }

}
