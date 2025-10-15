package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.club.Club;
import seedu.address.model.field.Name;
import seedu.address.model.person.Person;

import java.util.Optional;

/**
 * Removes a Person from a Club
 */
public class RemoveFromCommand extends Command {
    public static final String COMMAND_WORD = "remove_from";
    public static final String MESSAGE_ARGUMENTS = "Person: %1$s";
    public static final String MESSAGE_SUCCESS = "%1$s removed from %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a person from a specified club. "
            + "Parameters: ";

    public static final String MESSAGE_PERSON_NOT_FOUND = "Person not found";
    public static final String MESSAGE_CLUB_NOT_FOUND = "Club not found";
    private final Name personName;
    private final Name clubName;

    //Todo: Update later
    /**
     * @param personName of the person in the filtered person list to edit
     * @param clubName of the club in the filtered club list to edit
     */
    public RemoveFromCommand(Name personName, Name clubName) {
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

        Person person = optionalPerson.get();
        Club club = optionalClub.get();
        club.removeMember(person);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personName, clubName));
    }
}
