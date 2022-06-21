package app.view.controller;

import app.api.MatchApi;
import app.view.StageManager;
import app.view.State;
import app.view.View;
import app.view.node.MessageListView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class Messages implements Initializable {
    @FXML
    private TextField textField;

    @FXML
    private MessageListView messageList;

    private final StageManager stageManager;

    private final State state;

    private final MatchApi matchApi;

    private Thread thread;
    private boolean autoRefresh = true;

    @Autowired
    public Messages(StageManager stageManager, State state, MatchApi matchApi) {
        this.stageManager = stageManager;
        this.state = state;
        this.matchApi = matchApi;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageList.setSenderUsername(state.getUsername());
        fetchMessages();
        thread = new Thread(() -> {
            while (autoRefresh) {
                try {
                    Thread.sleep(2000);
                    Platform.runLater(this::fetchMessages);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void fetchMessages() {
        matchApi.getMatch(state.getCurrentMatch().getId())
                .thenAccept(match -> Platform.runLater(() -> messageList.getItems().setAll(match.getMessages())));
    }

    public void send() {
        matchApi.sendMessage(state.getCurrentMatch().getId(), textField.getText())
                .thenAccept(v -> {
                    textField.setText("");
                    fetchMessages();
                });
    }

    public void back() {
        this.autoRefresh = false;
        this.stageManager.changeView(View.MATCHES);
    }
}
