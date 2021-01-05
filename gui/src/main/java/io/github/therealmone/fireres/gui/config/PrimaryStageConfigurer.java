package io.github.therealmone.fireres.gui.config;

import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.val;

public class PrimaryStageConfigurer implements Configurer<Stage> {

    private static final String TITLE = "Fire resistance report generator";

    @Override
    public void config(Stage stage) {
        stage.setTitle(TITLE);

        val bounds = Screen.getPrimary().getVisualBounds();

        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        stage.setResizable(false);
    }

}
