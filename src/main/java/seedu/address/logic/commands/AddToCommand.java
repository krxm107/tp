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

    private final Index personIndex;
    private final Index clubIndex;

    //Todo: Update later
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a person to a club. \n" //I think a newline would look nice here
            //Todo: reformat Message_usage of other commands as well
            + "Parameters: "
            + PREFIX_MEMBER + "PERSON NAME "
            + PREFIX_CLUB + "CLUB NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEMBER + "John Doe "
            + PREFIX_CLUB + "Tennis Club";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person not found";
    public static final String MESSAGE_CLUB_NOT_FOUND = "Club not found";

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

        // Check if membership already exists
        Membership toAdd = new Membership(person, club);
        if (model.hasMembership(toAdd)) {
            throw new CommandException("The person is already in the club");
        }

        club.addMembership(toAdd);
        person.addMembership(toAdd);
        // model keep track of the generic membership without role. may change this later
        model.addMembership(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, person.getName().toString(), club.getName().toString()));
    }
}

