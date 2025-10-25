package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        // Add a listener to scroll to the newly added person.
        personList.addListener((ListChangeListener<Person>) c -> {
            while (c.next()) {
                // We are only interested in additions to the list.
                if (c.wasAdded()) {
                    // Get the person that was just added.
                    // c.getAddedSubList() returns a list of all items added.
                    // We'll scroll to the first one in the list of additions.
                    if (!c.getAddedSubList().isEmpty()) {
                        Person addedPerson = c.getAddedSubList().get(0);

                        // Use Platform.runLater to ensure this happens after the UI pass.
                        Platform.runLater(() -> {
                            // Scroll to the specific Person object. This is robust and
                            // works correctly even if the list is sorted.
                            personListView.scrollTo(addedPerson);
                        });
                    }
                }
            }
        });
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
