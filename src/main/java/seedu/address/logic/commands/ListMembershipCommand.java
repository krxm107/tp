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
 * Lists memberships of a person identified using it's displayed index from the address book.
 */
public class ListMembershipCommand extends Command {

    public static final String COMMAND_WORD = "list_memberships";

    public static final String MESSAGE_SUCCESS = "Listed all memberships";
    public static final String COMMAND_SHORT = "listmp"; // list members for person

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_SHORT
            + "): List all memberships of a person identified by its index number in the displayed list."
            + "\nParameters: INDEX (must be a positive integer) /[OPTIONAL KEYWORDS]\n"
            + "Optional keywords may be added to specify which membership statuses to show.\n"
            + "Keywords: a - active, c - cancelled, e - expired, p - pending expiration\n"
            + "Example: " + COMMAND_WORD + " 1 - shows all non-expired memberships\n"
            + "Example: " + COMMAND_WORD + " 1 /ap - shows all memberships that are active or pending expiration";

    public static final String MESSAGE_LIST_SUCCESS = "Listed all memberships";

    private final Index targetIndex;
    private final Predicate<Membership> predicate;

    /**
     * Creates a <code>ListMembershipCommand</code> with the targetIndex
     * and predicate for filtering membership statuses.
     */
    public ListMembershipCommand(Index targetIndex, Predicate<Membership> predicate) {
        this.targetIndex = targetIndex;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDisplay = lastShownList.get(targetIndex.getZeroBased());
        model.updateFilteredPersonList(person -> person.equals(personToDisplay));
        Predicate<Club> isMemberOf = club -> club.getMemberships().stream().filter(predicate)
                .map(Membership::getPerson).anyMatch(person -> person.equals(personToDisplay));
        model.updateFilteredClubList(isMemberOf);
        return new CommandResult(MESSAGE_LIST_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListMembershipCommand)) {
            return false;
        }

        ListMembershipCommand otherListMemberhipCommand = (ListMembershipCommand) other;
        return targetIndex.equals(otherListMemberhipCommand.targetIndex)
                && predicate.equals(otherListMemberhipCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("predicate", predicate)
                .toString();
    }
}
