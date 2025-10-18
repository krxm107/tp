package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * Removes a Person from a Club
 */
public class RemoveFromCommand extends Command {
    public static final String COMMAND_WORD = "remove_from";
    public static final String MESSAGE_SUCCESS = "%1$s removed from %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a person from a club\n"
            + "Person identified by the index number used in the displayed person list.\n"
            + "Club identified by the index number used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_MEMBER + "INDEX (must be a positive integer)\n"
            + PREFIX_NAME + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEMBER + "1 "
            + PREFIX_NAME + "3";

    private final Index personIndex;
    private final Index clubIndex;

    /**
     * @param personIndex of the person in the filtered person list to edit
     * @param clubIndex of the club in the filtered club list to edit
     */
    public RemoveFromCommand(Index personIndex, Index clubIndex) {
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

        // Check if membership doesn't exist
        Membership toRemove = new Membership(person, club);
        if (!model.hasMembership(toRemove)) {
            throw new CommandException("The person is not in the club");
        }
        club.removeMember(person);
        // this is to keep track of the generic Membership without caring about role, etc
        model.deleteMembership(toRemove);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personName, clubName));
    }
}
