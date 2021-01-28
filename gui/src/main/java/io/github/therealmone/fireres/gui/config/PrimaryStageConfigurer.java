package io.github.therealmone.fireres.gui.config;

import javafx.stage.Stage;

public class PrimaryStageConfigurer implements Configurer<Stage> {

    private static final String TITLE = "Fire resistance report generator";

    @Override
    public void config(Stage stage) {
        stage.setTitle(TITLE);

        stage.setWidth(1175);
        stage.setHeight(1000);

        stage.setResizable(false);
    }

}
