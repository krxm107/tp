package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.FindCommandPredicate;
import seedu.address.logic.parser.search.SearchParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book matching any number of search instructions.
 */
public class FindPersonCommand extends Command {

    public static final String COMMAND_WORD = "findp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose match all of "
            + "the specified search instructions and displays them as a list with index numbers. "
            + SearchParser.MESSAGE_USAGE
            + "Search Keywords: /n - search by name /t - search by tag (name)\n"
            + "Example: " + COMMAND_WORD + " /n alice bob /t friend /t NUS"
            + " - searches for all persons tagged as 'friend' and 'NUS' with names containing 'alice' or 'bob'";

    private final FindCommandPredicate<Person> predicate;

    public FindPersonCommand(FindCommandPredicate<Person> predicate) {
        this.predicate = predicate;
    }

    public FindCommandPredicate<Person> getPredicate() {
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
