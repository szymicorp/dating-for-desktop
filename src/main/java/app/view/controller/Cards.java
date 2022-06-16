package app.view.controller;

import app.api.UserApi;
import app.view.StageManager;
import app.view.View;
import app.view.node.ProfileCard;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class Cards implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    private final UserApi userApi;

    private final StageManager stageManager;

    @Autowired @Lazy
    public Cards(UserApi userApi, StageManager stageManager) {
        this.userApi = userApi;
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchUnseenUsers();
        anchorPane.getChildren().addListener((ListChangeListener<Node>) c -> {
            if (anchorPane.getChildren().size() == 0) {
                fetchUnseenUsers();
            }
        });
    }

    private void fetchUnseenUsers() {
        try {
            var users = userApi.getUnseenUsers();
            for (var user : users) {
                var profileCard = new ProfileCard(
                        user,
                        card -> anchorPane.getChildren().remove(card),
                        u -> userApi.like(user.getId()),
                        u -> userApi.dislike(user.getId())
                );
                anchorPane.getChildren().add(profileCard);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void goToMatches() {
        stageManager.changeView(View.MATCHES);
    }
}
