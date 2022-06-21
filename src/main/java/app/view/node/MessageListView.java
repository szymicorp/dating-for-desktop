package app.view.node;

import app.model.Message;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class MessageListView extends ListView<Message> {
    private static final String MATCH_STYLE = "match-message";
    private static final String USER_STYLE = "user-message";
    private String senderUsername;

    public MessageListView() {
        this.getStylesheets().add(getClass().getResource("/app/view/css/messages.css").toExternalForm());
        this.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Message message, boolean empty) {
                if (!empty) {
                    this.setText(message.getContent());
                    if (senderUsername.equals(message.getSenderUsername())) {
                        this.getStyleClass().remove(MATCH_STYLE);
                        this.getStyleClass().add(USER_STYLE);
                    } else {
                        this.getStyleClass().remove(USER_STYLE);
                        this.getStyleClass().add(MATCH_STYLE);
                    }
                }
            }
        });
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }
}
