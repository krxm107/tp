package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.search.CombinedSearchPredicate;
import seedu.address.logic.search.parsers.SearchParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book matching any number of search conditions.
 */
public class FindPersonCommand extends Command {
    public static final String COMMAND_WORD = "find_person";
    public static final String COMMAND_SHORT = "findp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_SHORT
            + "): Find persons that match all of "
            + "the specified search conditions and display them in the list on the right.\n"
            + SearchParser.MESSAGE_USAGE
            + "Search Conditions: a/ - by address, e/ - by email, n/ - by name, p/ - by phone, t/ - by tag, "
            + "s/ - by membership statuses (a, c, e, p)\n"
            + "Example: " + COMMAND_WORD + " n/ alice bob t/ friend t/ paid"
            + " - searches for persons tagged as 'friend' and 'paid' with names containing 'alice' or 'bob'";

    private final CombinedSearchPredicate<Person> predicate;

    public FindPersonCommand(CombinedSearchPredicate<Person> predicate) {
        this.predicate = predicate;
    }

    public CombinedSearchPredicate<Person> getPredicate() {
        return predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindPersonCommand)) {
            return false;
        }

        FindPersonCommand otherListCommand = (FindPersonCommand) other;
        return predicate.equals(otherListCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
