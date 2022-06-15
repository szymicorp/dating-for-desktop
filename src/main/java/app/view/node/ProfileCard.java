package app.view.node;

import app.model.User;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.function.Consumer;

public class ProfileCard extends VBox {
    private double mouseX;
    private double mouseY;

    public ProfileCard(
            User user,
            Consumer<Node> removeCard,
            Consumer<User> like,
            Consumer<User> dislike
    ) {
        this.getStyleClass().add("card-pane");
        this.getStylesheets().add(getClass().getResource("/app/view/css/profile-card.css").toExternalForm());
        var clip = new Rectangle(350, 350);
        clip.setArcHeight(40);
        clip.setArcWidth(40);
        var photo = new ImageView(user.getPhotos().get(0));
        photo.getStyleClass().add("profile-image");
        photo.setClip(clip);
        photo.setFitHeight(350);
        photo.setPickOnBounds(true);
        photo.setPreserveRatio(true);
        var nameAndAge = new Label(user.getFirstName() + " " + user.getLastName() + " " + user.getAge());
        nameAndAge.getStyleClass().add("name");
        var bio = new Label(user.getBio());
        var interests = new Label(
                "Interests: " + String.join(", ", user.getInterests())
        );
        getChildren().addAll(photo, nameAndAge, bio, interests);

        setOnMousePressed(mouseEvent -> {
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();
        });

        setOnMouseDragged(mouseEvent -> {
            setLayoutX(mouseEvent.getSceneX() - mouseX);
            setLayoutY(mouseEvent.getSceneY() - mouseY - 75);
        });

        setOnMouseReleased(mouseEvent -> {
            var x =mouseEvent.getSceneX();
            if (x < 0) {
                dislike.accept(user);
                removeCard.accept(this);
            } else if (x > 400) {
                like.accept(user);
                removeCard.accept(this);
            } else {
                setLayoutX(0);
                setLayoutY(0);
            }
        });
    }
}
