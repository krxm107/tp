package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.club.Club;
import seedu.address.model.field.Name;
import seedu.address.model.person.Person;

/**
 * Adds a Person to a Club
 */
public class AddToCommand extends Command {
    public static final String COMMAND_WORD = "add_to";
    public static final String MESSAGE_SUCCESS = "%1$s added to %2$s";
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

    private final Name personName;
    private final Name clubName;

    /**
     * @param personName of the person in the filtered person list to edit
     * @param clubName of the club in the filtered club list to edit
     */
    public AddToCommand(Name personName, Name clubName) {
        requireAllNonNull(personName);
        this.personName = personName;
        this.clubName = clubName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Optional<Person> optionalPerson = model.getAddressBook().getPersonByName(personName);
        Optional<Club> optionalClub = model.getAddressBook().getClubByName(clubName);

        if (optionalPerson.isEmpty()) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        if (optionalClub.isEmpty()) {
            throw new CommandException(MESSAGE_CLUB_NOT_FOUND);
        }

        //Todo: Update role handling
        Person person = optionalPerson.get();
        Club club = optionalClub.get();
        club.addMember(person, "member");

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personName, clubName));
    }
}

