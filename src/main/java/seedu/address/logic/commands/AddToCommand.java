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
    private final Index personIndex;
    private final Index clubIndex;

    /**
     * @param personIndex of the person in the filtered person list to edit
     * @param clubIndex of the club in the filtered club list to edit
     */
    public AddToCommand(Index personIndex, Index clubIndex) {
        requireAllNonNull(personIndex, clubIndex);
        this.personIndex = personIndex;
        this.clubIndex = clubIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Club> lastShownClubList = model.getFilteredClubList();

        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (clubIndex.getZeroBased() >= lastShownClubList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLUB_DISPLAYED_INDEX);
        }
        Person person = lastShownPersonList.get(personIndex.getZeroBased());
        Club club = lastShownClubList.get(clubIndex.getZeroBased());
        String personName = person.getName().toString();
        String clubName = club.getName().toString();

        // Check if membership already exists
        Membership toAdd = new Membership(person, club);
        if (model.hasMembership(toAdd)) {
            throw new CommandException("The person is already in the club");
        }

        club.addMembership(toAdd);
        person.addMembership(toAdd);
        // model keep track of the generic membership without role. may change this later
        model.addMembership(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                personName,
                clubName));
    }
}

