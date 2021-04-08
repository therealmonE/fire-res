package io.github.therealmone.fireres.gui;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.configurer.PrimaryStageConfigurer;
import io.github.therealmone.fireres.gui.controller.common.MainScene;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.val;

public class GraphicalInterface {

    @Inject
    private PrimaryStageConfigurer primaryStageConfigurer;

    @Inject
    private FxmlLoadService fxmlLoadService;

    public void start(Stage stage) {
        primaryStageConfigurer.config(stage);
        val mainScene = fxmlLoadService.loadComponent(MainScene.class, null,
                component -> component.setPrimaryStage(stage));

        stage.setScene(new Scene(mainScene.getComponent()));
        stage.show();
    }

}
