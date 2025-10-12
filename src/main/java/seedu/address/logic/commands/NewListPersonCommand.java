package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book matching any number of search instructions.
 */
public class NewListPersonCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose match any of "
            + "the specified search instructions and displays them as a list with index numbers.\n"
            + "Parameters: \\SEARCH_KEYWORD1 [SEARCH_PARAMETERS1] \\SEARCH_KEYWORD2 [SEARCH_PARAMETERS2]...\n"
            + "Example: " + COMMAND_WORD + " \\n alice bob charlie";

    private final Predicate<Person> predicate;

    public NewListPersonCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
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
        if (!(other instanceof FindCommand)) {
            return false;
        }

        NewListPersonCommand otherListCommand = (NewListPersonCommand) other;
        return predicate.equals(otherListCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
