package app.view.controller;

import app.api.UserApi;
import app.model.User;
import app.view.node.ProfileCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class Cards implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    private final UserApi userApi;

    private List<User> users;

    @Autowired
    public Cards(UserApi userApi) {
        this.userApi = userApi;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            users = userApi.getUnseenUsers();
            var sublist = users;
            if (users.size() > 9) {
                sublist = sublist.subList(0, 9);
            }
            for (var user : sublist) {
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
}
