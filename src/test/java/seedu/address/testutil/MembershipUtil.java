package seedu.address.testutil;

import seedu.address.logic.commands.AddToCommand;
import seedu.address.model.membership.Membership;

/**
 * A utility class for Membership.
 */
public class MembershipUtil {

    /**
     * Returns an add command string for adding the {@code membership}.
     */
    public static String getAddToCommand(Membership membership) {
        return AddToCommand.COMMAND_WORD + " " + getMembershipDetails(membership);
    }

    /**
     * Returns the part of command string for the given {@code membership}'s details.
     */
    public static String getMembershipDetails(Membership membership) {
        // This is a placeholder. You'll need to decide how to represent membership details in a command.
        // For example, you might use person and club names or IDs.
        // Assuming it uses indexes for person and club
        return "1 1";
    }
}
