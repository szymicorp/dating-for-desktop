package app.view.controller;

import app.api.UserApi;
import app.model.Match;
import app.view.StageManager;
import app.view.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class Matches implements Initializable {
    @FXML
    private ListView<Match> matchList;

    private final UserApi userApi;

    private final StageManager stageManager;

    @Autowired
    public Matches(UserApi userApi, StageManager stageManager) {
        this.userApi = userApi;
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userApi.getUserMatches().thenAccept(
                matches -> matchList.getItems().addAll(matches)
        );
    }

    public void back() {
        stageManager.changeView(View.CARDS);
    }
}
