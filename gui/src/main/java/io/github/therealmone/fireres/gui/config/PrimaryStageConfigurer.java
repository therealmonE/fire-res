package io.github.therealmone.fireres.gui.config;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.SneakyThrows;

public class PrimaryStageConfigurer implements Configurer<Stage> {

    private static final String TITLE = "Генератор отчетов о тестировании на огнестойкость";

    @SneakyThrows
    @Override
    public void config(Stage stage) {
        stage.setTitle(TITLE);
        stage.setMinWidth(510);
        stage.setMinHeight(815);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.requestFocus();
        stage.getIcons().add(new Image(getClass().getResource("/image/logo.png").openStream()));
    }

}
