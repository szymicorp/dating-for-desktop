package app.view;

import app.config.SpringFXMLLoader;
import app.config.StageReadyEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class StageManager implements ApplicationListener<StageReadyEvent> {
    private final SpringFXMLLoader springFXMLLoader;
    private Stage primaryStage;

    @Autowired
    public StageManager(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        primaryStage = event.getPrimaryStage();
        changeView(View.LOGIN);
    }

    public void changeView(View view) {
        var node = loadNode(view.getFxml());
        var scene = prepareScene(node);
        show(scene, view.getTitle());
    }

    private Scene prepareScene(Parent node){
        Scene scene = primaryStage.getScene();
        if (scene == null) {
            scene = new Scene(node);
        }
        scene.setRoot(node);

        return scene;
    }

    private void show(Scene scene, String title) {
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private Parent loadNode(String fxml) {
        Parent node = null;
        try {
             node = springFXMLLoader.load(fxml);
             Objects.requireNonNull(node, "FXML node can't be null");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }
}
