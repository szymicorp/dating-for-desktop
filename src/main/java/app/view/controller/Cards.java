package app.view.controller;

import app.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class Cards implements Initializable {

    @FXML
    private Rectangle rect;

    private Draggable draggable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        draggable = new Draggable();
        draggable.makeDraggable(rect);
    }

    private static class Draggable {
        private double mouseX;
        private double mouseY;

        public void makeDraggable(Node node) {
            node.setOnMousePressed(mouseEvent -> {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();
            });

            node.setOnMouseDragged(mouseEvent -> {
                node.setLayoutX(mouseEvent.getSceneX() - mouseX);
                node.setLayoutY(mouseEvent.getSceneY() - mouseY - 50);
            });
        }
    }
}
