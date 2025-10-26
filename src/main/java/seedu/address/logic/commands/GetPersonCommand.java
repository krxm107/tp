package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CopyUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.GetPersonMessageParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Copies the details of a person, identified using it's displayed index, to the user's clipboard.
 */
public class GetPersonCommand extends Command {

    public static final String COMMAND_WORD = "get_person";
    public static final String COMMAND_SHORT = "getp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (" + COMMAND_SHORT
            + "): Copies details of a person identified by the index number to the user's clipboard.\n"
            + "Parameters: INDEX (must be a positive integer) /[OPTIONAL KEYWORDS]\n"
            + "Optional keywords may be added to copy specified fields only.\n"
            + "Keywords: n - name, p - phone, e - email, a - address, m - memberships\n"
            + "Example: " + COMMAND_WORD + " 1 - copies name, phone, email, address and tags\n"
            + "Example: " + COMMAND_WORD + " 1 /p - copies phone number only";

    public static final String MESSAGE_GET_PERSON_SUCCESS = "Copied: %1$s";
    public static final String MESSAGE_GET_PERSON_FAILURE = "Failed to copy person to clipboard";

    private final Index targetIndex;
    private final String keywords;

    /**
     * {@code keywords} refer to optional words used to specify what fields to copy.
     * Is an empty string if no words were entered by the user.
     */
    public GetPersonCommand(Index targetIndex, String keywords) {
        this.targetIndex = targetIndex;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToCopy = lastShownList.get(targetIndex.getZeroBased());
        String details = new GetPersonMessageParser().parse(personToCopy, keywords);
        copyToClipboard(details);

        return new CommandResult(String.format(MESSAGE_GET_PERSON_SUCCESS, details));
    }

    /**
     * Logic for copying text to clipboard. Extracted to allow testing.
     */
    public void copyToClipboard(String details) throws CommandException {
        try {
            CopyUtil.copyTextToClipboard(details);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_GET_PERSON_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GetPersonCommand)) {
            return false;
        }

        GetPersonCommand otherGetCommand = (GetPersonCommand) other;
        return targetIndex.equals(otherGetCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

}
