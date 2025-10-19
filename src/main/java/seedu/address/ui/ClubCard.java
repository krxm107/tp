package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.club.Club;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class ClubCard extends UiPart<Region> {

    private static final String FXML = "ClubListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Club club;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ClubCode} with the given {@code Club} and index to display.
     */
    public ClubCard(Club club, int displayedIndex) {
        super(FXML);
        this.club = club;
        id.setText(displayedIndex + ". ");
        name.setText(club.getName().fullName);

        if (club.getPhone().value.isEmpty()) {
            phone.setVisible(false);
            phone.setManaged(false);
        } else {
            phone.setText(club.getPhone().value);
        }

        if (club.getAddress().value.isEmpty()) {
            address.setVisible(false);
            address.setManaged(false);
        } else {
            address.setText(club.getAddress().value);
        }

        if (club.getEmail().value.isEmpty()) {
            email.setVisible(false);
            email.setManaged(false);
        } else {
            email.setText(club.getEmail().value);
        }

        club.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
