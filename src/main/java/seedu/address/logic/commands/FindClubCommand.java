package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.search.CombinedSearchPredicate;
import seedu.address.logic.search.parsers.SearchParser;
import seedu.address.model.Model;
import seedu.address.model.club.Club;

/**
 * Finds and lists all clubs in address book matching any number of search conditions.
 */
public class FindClubCommand extends Command {
    public static final String COMMAND_WORD = "find_club";
    public static final String COMMAND_SHORT = "findc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_SHORT
            + "): Find clubs that match all of "
            + "the specified search conditions and display them in the list on the left.\n"
            + SearchParser.MESSAGE_USAGE
            + "Search Conditions: a/ - by address, e/ - by email, n/ - by name, p/ - by phone, t/ - by tag, "
            + "s/ - by existing member statuses (a, c, e, p)\n"
            + "Example: " + COMMAND_WORD + " n/ tennis basketball t/ school t/ evening"
            + " - searches for clubs tagged with 'school' and 'evening' that contain either 'tennis' or 'basketball'";

    private final CombinedSearchPredicate<Club> predicate;

    public FindClubCommand(CombinedSearchPredicate<Club> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredClubList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CLUBS_LISTED_OVERVIEW, model.getFilteredClubList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindClubCommand)) {
            return false;
        }

        FindClubCommand otherListCommand = (FindClubCommand) other;
        return predicate.equals(otherListCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
