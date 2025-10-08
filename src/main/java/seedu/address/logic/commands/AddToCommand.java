package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Changes the remark of an existing person in the address book.
 */
public class AddToCommand extends Command {

    public static final String COMMAND_WORD = "add_to";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Hello from add_to");
    }
}

