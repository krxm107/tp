package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
    public static final String MESSAGE_ARGUMENTS = "Person: %1$s";
    public static final String MESSAGE_SUCCESS = "%1$s added to %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a person to a specified club. "
            + "Parameters: ";

    private final Name personName;
    private final Name clubName;

    //Todo: Update later
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
        Person person = model.getAddressBook().getPersonByName(personName);
        Club club = model.getAddressBook().getClubByName(clubName);

        //Todo: Update role handling
        club.addMember(person, "member");
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personName, clubName));
    }
}

