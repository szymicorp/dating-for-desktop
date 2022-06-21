package app.view.controller;

import app.api.UserApi;
import app.model.Match;
import app.view.StageManager;
import app.view.State;
import app.view.View;
import app.view.node.MatchListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class Matches implements Initializable {
    @FXML
    private MatchListView matchList;

    private final UserApi userApi;

    private final StageManager stageManager;

    private final State state;

    @Autowired
    public Matches(UserApi userApi, StageManager stageManager, State state) {
        this.userApi = userApi;
        this.stageManager = stageManager;
        this.state = state;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.matchList.setCellAction(this::goToMatchMessages);
        this.userApi.getUserMatches().thenAccept(
                matches -> matchList.getItems().addAll(matches)
        );
    }

    private void goToMatchMessages(Match match) {
        state.setCurrentMatch(match);
        stageManager.changeView(View.MESSAGES);
    }

    public void back() {
        stageManager.changeView(View.CARDS);
    }
}
