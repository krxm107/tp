package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.club.Club;

/**
 * Finds and lists all clubs in address book matching any number of search instructions.
 */
public class FindClubCommand extends Command {

    public static final String COMMAND_WORD = "findc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all clubs whose match each of "
            + "the specified search instructions and displays them as a list with index numbers.\n"
            + "Parameters: /SEARCH_KEYWORD1 [SEARCH_PARAMETERS1] /SEARCH_KEYWORD2 [SEARCH_PARAMETERS2]...\n"
            + "Search Keywords: /n - search by name /t - search by tag (name)\n"
            + "Example: " + COMMAND_WORD + " /n tennis basketball /t school"
            + " - searches for all clubs tagged with 'school' that contain either 'tennis' or 'basketball'";

    private final Predicate<Club> predicate;

    public FindClubCommand(Predicate<Club> predicate) {
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
        if (!(other instanceof FindCommand)) {
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
