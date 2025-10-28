package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CopyUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.membership.Membership;
import seedu.address.model.membership.MembershipEvent;
import seedu.address.model.person.Person;

/**
 * Copies the details of a person, identified using it's displayed index, to the user's clipboard.
 */
public class GetHistoryCommand extends Command {

    public static final String COMMAND_WORD = "get_history";
    public static final String COMMAND_SHORT = "geth";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_SHORT
            + "): Copies membership history of a person identified by the index number to the user's clipboard.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_GET_HISTORY_SUCCESS = "Copied membership history:\n%2$s";
    public static final String MESSAGE_GET_HISTORY_FAILURE = "Failed to copy membership history for %1s to clipboard";
    public static final String MESSAGE_NO_MEMBERSHIPS = "%1$s has no membership.";

    private final Index targetIndex;

    public GetHistoryCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Logic for copying text to clipboard. Extracted to allow testing.
     */
    public void copyToClipboard(String details, Person person) throws CommandException {
        try {
            CopyUtil.copyTextToClipboard(details);
        } catch (Exception e) {
            throw new CommandException(String.format(MESSAGE_GET_HISTORY_FAILURE, person.getName().fullName));
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_DETAILED, targetIndex.getOneBased()));
        }

        Person person = lastShownList.get(targetIndex.getZeroBased());
        List<Membership> memberships = person.getMemberships();

        if (memberships.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_MEMBERSHIPS, person.getName().fullName));
        }

        String historyText = formatHistory(person);
        copyToClipboard(historyText, person);
        return new CommandResult(String.format(MESSAGE_GET_HISTORY_SUCCESS, person.getName().fullName, historyText));
    }

    // note: formatHistory can be copied to GetPersonMessageParser if needed
    /**
     * Formats the membership event history for a given person into a readable string.
     */
    private String formatHistory(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append("Membership history for ").append(person.getName().fullName).append(":\n");
        ObservableList<Membership> memberships = person.getMemberships();
        if (memberships.isEmpty()) {
            sb.append(String.format(MESSAGE_NO_MEMBERSHIPS, person.getName().fullName));
        }
        for (Membership membership : memberships) {
            sb.append("\nClub: ").append(membership.getClubName()).append("\n");
            for (MembershipEvent event : membership.getMembershipEventHistory()) {
                // Using the toString() method we defined in MembershipEvent
                sb.append("  - ").append(event.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GetHistoryCommand)) {
            return false;
        }

        GetHistoryCommand otherGetCommand = (GetHistoryCommand) other;
        return targetIndex.equals(otherGetCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

}
