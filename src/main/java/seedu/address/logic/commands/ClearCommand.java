package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all data from the address book. "
            + "This action cannot be undone.\n"
            + "Parameters: YES (must be capitalized to confirm)\n";
    public static final String MESSAGE_MISSING_CONFIRMATION =
            "Enter \"clear YES\" to confirm clearing the entire address book.\n";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    private final boolean clearFlag;
    /**
     * Constructs a ClearCommand.
     *
     * @param clearFlag A boolean flag that must be true to execute the command.
     * @throws IllegalArgumentException if clearFlag is false.
     */
    public ClearCommand(boolean clearFlag) {
        this.clearFlag = clearFlag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!clearFlag) {
            throw new CommandException(MESSAGE_MISSING_CONFIRMATION + MESSAGE_USAGE);
        }
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
