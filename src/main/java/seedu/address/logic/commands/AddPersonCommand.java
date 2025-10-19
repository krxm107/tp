package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 * <p>
 * Phone number is optional — the user may omit {@code p/PHONE}.
 * </p>
 */
public class AddPersonCommand extends Command {

    public static final String COMMAND_WORD = "add_person";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person. "
                        + "Required: " + PREFIX_NAME + "NAME "
                        + "Optional: [" + PREFIX_EMAIL + "EMAIL] ["
                        + PREFIX_PHONE + "PHONE] ["
                        + PREFIX_ADDRESS + "ADDRESS]"
                        + "Examples:\n  " + COMMAND_WORD + " "
                        + PREFIX_NAME + "John Doe "
                        + PREFIX_EMAIL + "johnd@example.com "
                        + PREFIX_PHONE + "98765432\n  "
                        + COMMAND_WORD + " "
                        + PREFIX_NAME + "Jane "
                        + PREFIX_EMAIL + "jane@abc.com "
                        + PREFIX_ADDRESS + "1 Road";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     */
    public AddPersonCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPersonCommand)) {
            return false;
        }

        AddPersonCommand otherAddPersonCommand = (AddPersonCommand) other;
        return toAdd.equals(otherAddPersonCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
