package app.view.controller;

import app.api.Api;
import app.api.LoginApi;
import app.view.StageManager;
import app.view.View;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class Login implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Label message;

    PauseTransition visiblePause;

    private LoginApi loginApi;

    private StageManager stageManager;

    @Autowired @Lazy
    public Login(LoginApi loginApi, StageManager stageManager) {
        this.loginApi = loginApi;
        this.stageManager = stageManager;
        visiblePause = new PauseTransition(
                Duration.seconds(5)
        );
        visiblePause.setOnFinished(event -> message.setVisible(false));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.disableProperty().bind(
                Bindings.or(
                        username.textProperty().isEmpty(),
                        password.textProperty().isEmpty()
                )
        );
    }

    public void login() {
        loginApi.login(username.getText(), password.getText())
                .thenAccept(response -> {
                    var statusCode = response.statusCode();
                    var headers = response.headers().map();
                    handleResponse(statusCode, headers);
                });
    }

    private void handleResponse(int statusCode, Map<String, List<String>> headers) {
        if (!(statusCode == 200)) {
            showMessage();
        } else {
            var cookies = headers.get("set-cookie");
            handleCookie(cookies);
        }
    }

    private void handleCookie(List<String> cookies) {
        if (cookies != null && cookies.size() > 0) {
            Api.setAuthCookie(cookies.get(0));
            stageManager.changeView(View.CARDS);
        } else {
            showMessage();
        }
    }

    private void showMessage() {
        message.setVisible(true);
        Platform.runLater(() -> message.setText("Login unsuccessful, check your credentials"));
        visiblePause.play();
    }
}
