package app.view;

import app.config.StageReadyEvent;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageManager implements ApplicationListener<StageReadyEvent> {
    private Stage primaryStage;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        primaryStage = event.getPrimaryStage();
        show();
    }

    public void show() {
    }
}
