package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLUBS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListClubCommand extends Command {

    public static final String COMMAND_WORD = "clubs";

    public static final String MESSAGE_SUCCESS = "Listed all clubs";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredClubList(PREDICATE_SHOW_ALL_CLUBS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
