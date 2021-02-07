package io.github.therealmone.fireres.gui;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.config.PrimaryStageConfigurer;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.stage.Stage;
import lombok.val;

public class GraphicalInterface {

    @Inject
    private PrimaryStageConfigurer primaryStageConfigurer;

    @Inject
    private FxmlLoadService fxmlLoadService;

    public void start(Stage stage) {
        primaryStageConfigurer.config(stage);
        val mainScene = fxmlLoadService.loadMainScene(stage);

        stage.setScene(mainScene);
        stage.show();
    }

}
