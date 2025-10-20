package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddPersonCommand}.
 */
public class AddPersonCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddPersonCommand(validPerson), model,
                String.format(AddPersonCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddPersonCommand(personInList), model,
                AddPersonCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateEmail_throwsCommandException() {
        Model model = new ModelManager();
        Person alice = new PersonBuilder().withName("Alice Pauline").withEmail("alice@example.com").build();
        model.addPerson(alice);

        // Same email, different name -> should be caught as duplicate
        Person duplicateByEmail = new PersonBuilder().withName("Alicia").withEmail("alice@example.com").build();

        AddPersonCommand add = new AddPersonCommand(duplicateByEmail);
        assertThrows(CommandException.class, () -> add.execute(model));
    }

    @Test
    public void execute_sameNameDifferentEmail_success() throws Exception {
        Model model = new ModelManager();
        Person alice = new PersonBuilder().withName("Alice Pauline").withEmail("alice@example.com").build();
        model.addPerson(alice);

        Person sameNameDifferentEmail = new PersonBuilder().withName("Alice Pauline").withEmail("alice+1@example.com").build();

        AddPersonCommand add = new AddPersonCommand(sameNameDifferentEmail);
        add.execute(model); // no exception == success
    }
}
