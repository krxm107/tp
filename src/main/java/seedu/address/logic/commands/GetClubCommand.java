package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Function;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CopyUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.club.Club;

/**
 * Copies the details of a club, identified using it's displayed index, to the user's clipboard.
 */
public class GetClubCommand extends Command {

    public static final String COMMAND_WORD = "get_club";
    public static final String COMMAND_SHORT = "getc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_SHORT
            + "): Copies details of a club identified by the index number to the user's clipboard.\n"
            + "Parameters: INDEX (must be a positive integer) [OPTIONAL_KEYWORD]\n"
            + "An optional keyword may be added to specify what to copy.\n"
            + "Keywords: n - name, p - phone, e - email, a - address, m - members, * - full details\n"
            + "The '*' keyword specifies all club details to be copied "
            + "along with existing (non-canceled) members' details.\n"
            + "Example: " + COMMAND_WORD + " 1 - copies name, phone, email, address and tags\n"
            + "Example: " + COMMAND_WORD + " 1 p - copies phone number\n"
            + "Example: " + COMMAND_WORD + " 1 * - copies full club and member details";

    public static final String MESSAGE_GET_CLUB_SUCCESS = "Copied: %1$s";
    public static final String MESSAGE_GET_CLUB_FAILURE = "Failed to copy club to clipboard";

    private final Index targetIndex;
    private final Function<Club, String> mapper;

    /**
     * {@code mapper} Is the function that maps the club to the copied string.
     */
    public GetClubCommand(Index targetIndex, Function<Club, String> mapper) {
        this.targetIndex = targetIndex;
        this.mapper = mapper;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Club> lastShownList = model.getFilteredClubList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
        }

        Club clubToCopy = lastShownList.get(targetIndex.getZeroBased());
        String details = mapper.apply(clubToCopy);
        copyToClipboard(details);

        return new CommandResult(String.format(MESSAGE_GET_CLUB_SUCCESS, details));
    }

    /**
     * Logic for copying text to clipboard. Extracted to allow testing.
     */
    public void copyToClipboard(String details) throws CommandException {
        try {
            CopyUtil.copyTextToClipboard(details);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_GET_CLUB_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GetClubCommand)) {
            return false;
        }

        GetClubCommand otherGetCommand = (GetClubCommand) other;
        return targetIndex.equals(otherGetCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

}
