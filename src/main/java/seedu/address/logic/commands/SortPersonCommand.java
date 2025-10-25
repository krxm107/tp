package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all persons by the specified fields and displays them in a list with index numbers.
 */
public class SortPersonCommand extends Command {

    public static final String COMMAND_WORD = "sort_person";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons by "
            + "the specified fields (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [n/] [p/] [e/] [a/] …\u200B\n"
            + "Example: " + COMMAND_WORD + " n/ p/";

    private final Comparator<Person> personComparator;

    public SortPersonCommand(Comparator<Person> personComparator) {
        this.personComparator = personComparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortFilteredPersonList(personComparator);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortPersonCommand)) {
            return false;
        }

        SortPersonCommand otherSortPersonCommand = (SortPersonCommand) other;
        return personComparator.equals(otherSortPersonCommand.personComparator);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("person comparator", personComparator)
                .toString();
    }
}
