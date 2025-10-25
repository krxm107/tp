package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.club.Club;

/**
 * Sorts all clubs by the specified fields and displays them in a list with index numbers.
 */
public class SortClubCommand extends Command {

    public static final String COMMAND_WORD = "sort_club";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all clubs by "
            + "the specified fields (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [n/] [p/] [e/] [a/] …\u200B\n"
            + "Example: " + COMMAND_WORD + " n/ p/";

    private final Comparator<Club> clubComparator;

    public SortClubCommand(Comparator<Club> clubComparator) {
        this.clubComparator = clubComparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortFilteredClubList(clubComparator);
        return new CommandResult(
                String.format(Messages.MESSAGE_CLUBS_LISTED_OVERVIEW, model.getFilteredClubList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortClubCommand)) {
            return false;
        }

        SortClubCommand otherSortClubCommand = (SortClubCommand) other;
        return clubComparator.equals(otherSortClubCommand.clubComparator);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("club comparator", clubComparator)
                .toString();
    }
}
