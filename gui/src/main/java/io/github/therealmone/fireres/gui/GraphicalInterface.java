package io.github.therealmone.fireres.gui;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.annotation.MainScene;
import io.github.therealmone.fireres.gui.config.PrimaryStageConfigurer;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicalInterface {

    @Inject
    private PrimaryStageConfigurer primaryStageConfigurer;

    @Inject
    @MainScene
    private Scene mainScene;

    public void start(Stage stage) throws Exception {
        primaryStageConfigurer.config(stage);

        stage.setScene(mainScene);
        stage.show();
    }

}
