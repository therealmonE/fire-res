package io.github.therealmone.fireres.gui;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.config.PrimaryStageConfigurer;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicalInterface {

    @Inject
    private PrimaryStageConfigurer primaryStageConfigurer;

    public void start(Stage stage, Scene mainScene) {
        primaryStageConfigurer.config(stage);

        stage.setScene(mainScene);
        stage.show();
    }

}
