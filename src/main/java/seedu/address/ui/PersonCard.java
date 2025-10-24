package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.membership.Membership;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

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
    @FXML
    private VBox memberships;
    @FXML
    private HBox phoneRow;
    @FXML
    private HBox addressRow;
    @FXML
    private HBox emailRow;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        if (person.getPhone().value.isEmpty()) {
            phoneRow.setVisible(false);
            phoneRow.setManaged(false);
        } else {
            phone.setText(person.getPhone().value);
        }

        if (person.getAddress().value.isEmpty()) {
            addressRow.setVisible(false);
            addressRow.setManaged(false);
        } else {
            address.setText(person.getAddress().value);
        }

        if (person.getEmail().value.isEmpty()) {
            emailRow.setVisible(false);
            emailRow.setManaged(false);
        } else {
            email.setText(person.getEmail().value);
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        person.getMemberships().stream()
                .sorted(Comparator.comparing(Membership::getClubName))
                .forEach(membership -> {
                    String clubName = membership.getClubName();
                    String exp = membership.getExpiryDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    Label membershipLabel = new Label(clubName + ": until " + exp);
                    membershipLabel.getStyleClass().add("membership-label");

                    switch (membership.getStatus()) {
                    case ACTIVE:
                        membershipLabel.getStyleClass().add("membership-active");
                        break;
                    case EXPIRED:
                        membershipLabel.getStyleClass().add("membership-expired");
                        break;
                    case PENDING_CANCELLATION:
                        membershipLabel.getStyleClass().add("membership-pending-cancellation");
                        break;
                    case CANCELLED:
                        membershipLabel.getStyleClass().add("membership-cancelled");
                        break;
                    default:
                        membershipLabel.getStyleClass().add("membership-active");
                        break;
                    }
                    memberships.getChildren().add(membershipLabel);
                });
    }
}
