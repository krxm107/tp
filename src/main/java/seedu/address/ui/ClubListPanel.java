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
import seedu.address.model.club.Club;

/**
 * Panel containing the list of clubs.
 */
public class ClubListPanel extends UiPart<Region> {
    private static final String FXML = "ClubListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ClubListPanel.class);

    @FXML
    private ListView<Club> clubListView;

    /**
     * Creates a {@code ClubListPanel} with the given {@code ObservableList}.
     */
    public ClubListPanel(ObservableList<Club> clubList) {
        super(FXML);
        clubListView.setItems(clubList);
        clubListView.setCellFactory(listView -> new ClubListViewCell());
        clubListView.getItems().addListener((ListChangeListener<Club>) change -> {
            Platform.runLater(() -> clubListView.scrollTo(change.getFrom()));
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Club} using a {@code ClubCard}.
     */
    class ClubListViewCell extends ListCell<Club> {
        @Override
        protected void updateItem(Club club, boolean empty) {
            super.updateItem(club, empty);

            if (empty || club == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ClubCard(club, getIndex() + 1).getRoot());
            }
        }
    }

}
