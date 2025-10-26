package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * Lists all members in a club identified using it's displayed index from the address book.
 */
public class ListMemberCommand extends Command {

    public static final String COMMAND_WORD = "list_members";
    public static final String COMMAND_SHORT = "listmc"; // list members for clubs

    public static final String MESSAGE_SUCCESS = "Listed all members";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_SHORT
            + "): List all members of a club identified by the index number used in the displayed club list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LIST_SUCCESS = "Listed all members";

    private final Index targetIndex;

    public ListMemberCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Club> lastShownList = model.getFilteredClubList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
        }

        Club clubToDisplay = lastShownList.get(targetIndex.getZeroBased());
        model.updateFilteredClubList(club -> club.equals(clubToDisplay));
        Predicate<Person> isInClub = person -> person.getMemberships().stream().map(Membership::getClub)
                .anyMatch(club -> club.equals(clubToDisplay));
        model.updateFilteredPersonList(isInClub);
        return new CommandResult(MESSAGE_LIST_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListMemberCommand)) {
            return false;
        }

        ListMemberCommand otherListMemberCommand = (ListMemberCommand) other;
        return targetIndex.equals(otherListMemberCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
