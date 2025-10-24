package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.club.Club;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 * <p>
 * Phone number and address are optional — the user may omit {@code p/PHONE} and {@code a/ADDRESS}
 * </p>
 */
public class AddPersonCommand extends Command {

    public static final String COMMAND_WORD = "add_person";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Jane "
            + PREFIX_EMAIL + "jane@abc.com ";

    public static final String MESSAGE_DUPLICATE_PERSON_EMAIL =
            "A person with this email already exists.";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private static final Logger logger = LogsCenter.getLogger(AddPersonCommand.class);

    private final Person personToAdd;
    private Index[] clubIndexes = null;

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     */
    public AddPersonCommand(Person person) {
        requireNonNull(person);
        personToAdd = person;
    }

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     * and add memberships to specified clubs.
     */
    public AddPersonCommand(Person person, Index[] clubIndexes) {
        requireNonNull(person);
        personToAdd = person;
        this.clubIndexes = clubIndexes;
    }

    private void addMembershipToAll(Model model, Person personToAdd, Club club) {
        Membership membershipToAdd = new Membership(personToAdd, club);
        club.addMembership(membershipToAdd);
        personToAdd.addMembership(membershipToAdd);
        model.addMembership(membershipToAdd);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        final String cls = AddPersonCommand.class.getName();
        final String mtd = "execute";
        logger.entering(cls, mtd, model);
        requireNonNull(model);


        logger.fine(() -> "Validating duplicate for person: " + personToAdd);
        try {
            if (model.hasPerson(personToAdd)) {
                logger.warning(() -> "AddPerson rejected (duplicate): " + personToAdd);
                CommandException ce = new CommandException(AddPersonCommand.MESSAGE_DUPLICATE_PERSON);
                // record the exception with source class/method for trace
                logger.throwing(cls, mtd, ce);
                throw ce;
            }

            model.addPerson(personToAdd);
            logger.info(() -> "Person added: " + personToAdd);

            if (clubIndexes != null) {
                List<Club> lastShownClubList = model.getFilteredClubList();
                for (Index clubIndex : clubIndexes) {
                    if (clubIndex.getZeroBased() >= lastShownClubList.size()) {
                        continue; // Skip to the next club index
                    }
                    Club club = lastShownClubList.get(clubIndex.getZeroBased());
                    addMembershipToAll(model, personToAdd, club);
                }
            }

            CommandResult result = new CommandResult(
                    String.format("New person added: %s",
                            Messages.format(personToAdd)));
            logger.exiting(cls, mtd, result);
            return result;

        } catch (CommandException e) {
            // Already logged via logger.throwing above; keep noise low here.
            logger.fine(() -> "AddPerson failed with CommandException: " + e.getMessage());
            logger.exiting(cls, mtd, e.getMessage());
            throw e;

        } catch (Exception e) {
            // Unexpected: include stack trace and precise origin
            logger.log(Level.SEVERE, "Unexpected error while adding person: " + personToAdd, e);
            logger.throwing(cls, mtd, e);
            logger.exiting(cls, mtd, "SEVERE: unexpected failure");
            throw new CommandException("An unexpected error occurred while adding this person.");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPersonCommand)) {
            return false;
        }

        AddPersonCommand otherAddPersonCommand = (AddPersonCommand) other;
        return personToAdd.equals(otherAddPersonCommand.personToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", personToAdd)
                .toString();
    }
}
