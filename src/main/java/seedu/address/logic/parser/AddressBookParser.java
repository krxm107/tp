package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddClubCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddToCommand;
import seedu.address.logic.commands.CancelMembershipCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteClubCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.EditClubCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterClubCommand;
import seedu.address.logic.commands.FilterPersonCommand;
import seedu.address.logic.commands.FindClubCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.GetClubCommand;
import seedu.address.logic.commands.GetPersonCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAllCommand;
import seedu.address.logic.commands.ListMemberCommand;
import seedu.address.logic.commands.ListMembershipCommand;
import seedu.address.logic.commands.ReactivateMembershipCommand;
import seedu.address.logic.commands.RemoveFromCommand;
import seedu.address.logic.commands.RenewMembershipCommand;
import seedu.address.logic.commands.SortClubCommand;
import seedu.address.logic.commands.SortPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        CommandList.addCommand(userInput);

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.strip());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord.toLowerCase()) {

        case AddPersonCommand.COMMAND_WORD, AddPersonCommand.COMMAND_SHORT:
            return new AddPersonCommandParser().parse(arguments);

        case AddClubCommand.COMMAND_WORD, AddClubCommand.COMMAND_SHORT:
            return new AddClubCommandParser().parse(arguments);

        case EditPersonCommand.COMMAND_WORD, EditPersonCommand.COMMAND_SHORT:
            return new EditPersonCommandParser().parse(arguments);

        case EditClubCommand.COMMAND_WORD, EditClubCommand.COMMAND_SHORT:
            return new EditClubCommandParser().parse(arguments);

        case DeletePersonCommand.COMMAND_WORD, DeletePersonCommand.COMMAND_SHORT:
            return new DeletePersonCommandParser().parse(arguments);

        case DeleteClubCommand.COMMAND_WORD, DeleteClubCommand.COMMAND_SHORT:
            return new DeleteClubCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        case FindClubCommand.COMMAND_WORD, FindClubCommand.COMMAND_SHORT:
            return new FindClubCommandParser().parse(arguments);

        case FindPersonCommand.COMMAND_WORD, FindPersonCommand.COMMAND_SHORT:
            return new FindPersonCommandParser().parse(arguments);

        case FilterPersonCommand.COMMAND_WORD, FilterPersonCommand.COMMAND_SHORT:
            return new FilterPersonCommandParser().parse(arguments);

        case FilterClubCommand.COMMAND_WORD, FilterClubCommand.COMMAND_SHORT:
            return new FilterClubCommandParser().parse(arguments);

        case ListAllCommand.COMMAND_WORD:
            return new ListAllCommand();

        case ListMemberCommand.COMMAND_WORD, ListMemberCommand.COMMAND_SHORT:
            return new ListMemberCommandParser().parse(arguments);

        case ListMembershipCommand.COMMAND_WORD, ListMembershipCommand.COMMAND_SHORT:
            return new ListMembershipCommandParser().parse(arguments);

        case AddToCommand.COMMAND_WORD, AddToCommand.COMMAND_SHORT:
            return new AddToCommandParser().parse(arguments);

        case RemoveFromCommand.COMMAND_WORD, RemoveFromCommand.COMMAND_SHORT:
            return new RemoveFromCommandParser().parse(arguments);

        case RenewMembershipCommand.COMMAND_WORD:
            return new RenewMembershipCommandParser().parse(arguments);

        case CancelMembershipCommand.COMMAND_WORD:
            return new CancelMembershipCommandParser().parse(arguments);

        case ReactivateMembershipCommand.COMMAND_WORD:
            return new ReactivateMembershipCommandParser().parse(arguments);

        case GetClubCommand.COMMAND_WORD, GetClubCommand.COMMAND_SHORT:
            return new GetClubCommandParser().parse(arguments);

        case GetPersonCommand.COMMAND_WORD, GetPersonCommand.COMMAND_SHORT:
            return new GetPersonCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SortClubCommand.COMMAND_WORD:
            return new SortClubCommandParser().parse(arguments);

        case SortPersonCommand.COMMAND_WORD:
            return new SortPersonCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
