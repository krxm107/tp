package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * Adds a Person to a Club
 */
public class AddToCommand extends Command {
    public static final String COMMAND_WORD = "add_to";
    public static final String MESSAGE_SUCCESS = "%1$s added to %2$s";

    //Todo: Update later
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a person to a club\n"
            + "Person identified by the index number used in the displayed person list.\n"
            + "Club identified by the index number used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_MEMBER + "INDEX (must be a positive integer)\n"
            + PREFIX_CLUB + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEMBER + "1 "
            + PREFIX_CLUB + "3";
    public static final String MESSAGE_DUPLICATE_MEMBERSHIP = "%1$s is already in %2$s";
    private final Index[] personIndexes;
    private final Index[] clubIndexes;

    /**
     * @param personIndexes of the person in the filtered person list to edit
     * @param clubIndexes of the club in the filtered club list to edit
     */
    public AddToCommand(Index[] personIndexes, Index[] clubIndexes) {
        requireAllNonNull(personIndexes, clubIndexes);
        this.personIndexes = personIndexes;
        this.clubIndexes = clubIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Club> lastShownClubList = model.getFilteredClubList();
        StringBuilder personNamesBuilder = new StringBuilder();
        StringBuilder clubNamesBuilder = new StringBuilder();
        StringBuilder outputMessageBuilder = new StringBuilder();

        for (Index clubIndex : clubIndexes) {
            if (clubIndex.getZeroBased() >= lastShownClubList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
            }
            Club club = lastShownClubList.get(clubIndex.getZeroBased());
            String clubName = club.getName().toString();
            clubNamesBuilder.append(clubName).append(", ");
            for (Index personIndex : personIndexes) {
                if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                Person person = lastShownPersonList.get(personIndex.getZeroBased());
                String personName = person.getName().toString();

                // Check if membership already exists
                Membership toAdd = new Membership(person, club);
                if (model.hasMembership(toAdd)) {
                    outputMessageBuilder
                            .append(String.format(MESSAGE_DUPLICATE_MEMBERSHIP, personName, clubName))
                            .append("\n");
                    continue;
                }
                //Add the person name here only once even if multiple clubs are added
                //Only add the person who are not already in the club
                if (clubIndex == clubIndexes[0]) {
                    personNamesBuilder.append(personName).append(", ");
                }

                club.addMembership(toAdd);
                person.addMembership(toAdd);
                // model keep track of the generic membership without role. may change this later
                model.addMembership(toAdd);
            }
        }

        // Remove trailing comma and space
        // Also handle the case where no new memberships were added
        if (personNamesBuilder.length() == 0) {
            String outputMessage = outputMessageBuilder.toString();
            return new CommandResult(outputMessage);
        }
        String personNames = personNamesBuilder.substring(0, personNamesBuilder.length() - 2);
        String clubNames = clubNamesBuilder.substring(0, clubNamesBuilder.length() - 2);
        outputMessageBuilder.append(String.format(MESSAGE_SUCCESS, personNames, clubNames));
        String outputMessage = outputMessageBuilder.toString();
        return new CommandResult(outputMessage);
    }
}

