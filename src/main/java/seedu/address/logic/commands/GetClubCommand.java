package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CopyUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.get.GetClubMessageParser;
import seedu.address.model.Model;
import seedu.address.model.club.Club;

/**
 * Copies the details of a club, identified using it's displayed index, to the user's clipboard.
 */
public class GetClubCommand extends Command {

    public static final String COMMAND_WORD = "get_club";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Copies all details of a club identified by the index number to the user's clipboard.\n"
            + "Optional keywords may be added to copy specified fields only\n"
            + "Keywords: n - name, p - phone, e - email, a - address, m - members\n"
            + "Parameters: INDEX (must be a positive integer) /[OPTIONAL KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " 1 - copies all details fully labeled\n"
            + "Example: " + COMMAND_WORD + " 1 /p - copies phone number only";

    public static final String MESSAGE_GET_CLUB_SUCCESS = "Copied: %1$s";
    public static final String MESSAGE_GET_CLUB_FAILURE = "Failed to copy club to clipboard";

    private final Index targetIndex;
    private final String keywords;

    /**
     * {@code keywords} refer to optional words used to specify what fields to copy.
     * Is an empty string if no words were entered by the user.
     */
    public GetClubCommand(Index targetIndex, String keywords) {
        this.targetIndex = targetIndex;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Club> lastShownList = model.getFilteredClubList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
        }

        Club clubToCopy = lastShownList.get(targetIndex.getZeroBased());
        String details = new GetClubMessageParser().parse(clubToCopy, keywords);
        try {
            CopyUtil.copyTextToClipboard(details);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_GET_CLUB_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_GET_CLUB_SUCCESS, details));
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
