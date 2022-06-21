package app.view.node;

import app.model.Match;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

import java.util.function.Consumer;

public class MatchListView extends ListView<Match> {
    private Consumer<Match> cellAction;

    public MatchListView() {
        this.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Match> call(ListView<Match> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Match match, boolean empty) {
                        this.setStyle("-fx-font-size: 2em");
                        if (!empty) {
                            var user = match.getUser();
                            this.setText(user.getFirstName() + " " + user.getLastName());
                            var circle = new Circle(25, 25, 25);
                            this.setGraphicTextGap(20);
                            circle.setFill(new ImagePattern(new Image(user.getPhotos().get(0))));
                            this.setGraphic(circle);
                            this.setOnMouseClicked(event -> cellAction.accept(match));
                        }
                    }
                };
            }
        });
    }

    public void setCellAction(Consumer<Match> cellAction) {
        this.cellAction = cellAction;
    }
}
