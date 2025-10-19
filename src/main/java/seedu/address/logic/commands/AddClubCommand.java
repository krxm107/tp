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
import seedu.address.model.club.Club;

/**
 * Adds a club to the address book.
 *
 * <p>
 * Phone number is optional — the user may omit {@code p/PHONE}.
 * </p>
 */
public class AddClubCommand extends Command {

    public static final String COMMAND_WORD = "add_club";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a club to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Jane "
            + PREFIX_EMAIL + "jane@abc.com "
            + PREFIX_ADDRESS + "1 Road";

    public static final String MESSAGE_SUCCESS = "New club added: %1$s";
    public static final String MESSAGE_DUPLICATE_CLUB = "This club already exists in the address book";

    private final Club toAdd;

    /**
     * Creates an AddClubCommand to add the specified {@code Club}
     */
    public AddClubCommand(Club club) {
        requireNonNull(club);
        toAdd = club;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasClub(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLUB);
        }

        model.addClub(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddClubCommand)) {
            return false;
        }

        AddClubCommand otherAddClubCommand = (AddClubCommand) other;
        return toAdd.equals(otherAddClubCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
