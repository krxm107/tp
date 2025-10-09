package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.club.Club;
import seedu.address.model.field.Name;
import seedu.address.model.person.Person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Changes the remark of an existing person in the address book.
 */
public class AddToCommand extends Command {

    private final Name name;

    public static final String COMMAND_WORD = "add_to";
    public static final String MESSAGE_ARGUMENTS = "Person: %1$s";

    //Todo: Update later
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a person to a specified club. "
            + "Parameters: ";

    public AddToCommand(Name name) {
        requireAllNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        Person person = model.getAddressBook().getPersonByName(name);
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, person));
    }
}

