package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalClubs.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.club.Club;

/**
 * Contains integration tests (interaction with the Model) for {@code AddClubCommand}.
 */
public class AddClubCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_duplicateClub_throwsCommandException() {
        Club clubInList = model.getAddressBook().getClubList().get(0);
        assertCommandFailure(new AddClubCommand(clubInList), model,
                AddClubCommand.MESSAGE_DUPLICATE_CLUB);
    }

}
