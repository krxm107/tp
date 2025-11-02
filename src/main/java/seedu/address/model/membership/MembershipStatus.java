package seedu.address.model.membership;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the status of a Membership.
 */
public enum MembershipStatus {
    ACTIVE,
    EXPIRED,
    PENDING_CANCELLATION,
    CANCELLED;

    /**
     * Returns <code>MembershipStatus</code>es that should be displayed by default in
     * a membership or get command result.
     */
    public static List<MembershipStatus> getDefaultStatuses() {
        return Arrays.asList(ACTIVE, EXPIRED, PENDING_CANCELLATION);
    }

    /**
     * Returns <code>MembershipStatus</code>es as specified by args, in the context of
     * a membership or get command.
     */
    public static List<MembershipStatus> getStatuses(String args) {
        List<MembershipStatus> statuses = new ArrayList<>();
        if (args.contains("a")) {
            statuses.add(ACTIVE);
        }
        if (args.contains("e")) {
            statuses.add(EXPIRED);
        }
        if (args.contains("p")) {
            statuses.add(PENDING_CANCELLATION);
        }
        if (args.contains("c")) {
            statuses.add(CANCELLED);
        }
        return statuses;
    }

    public static boolean containsStatus(String args) {
        return args.toLowerCase().matches("[aepc\\s]*");
    }
}
