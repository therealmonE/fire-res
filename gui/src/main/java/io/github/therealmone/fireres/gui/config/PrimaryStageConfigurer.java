package io.github.therealmone.fireres.gui.config;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.model.Logos;
import javafx.stage.Stage;
import lombok.SneakyThrows;

public class PrimaryStageConfigurer implements Configurer<Stage> {

    private static final String TITLE = "Генератор отчетов о тестировании на огнестойкость";

    @Inject
    private Logos logos;

    @SneakyThrows
    @Override
    public void config(Stage stage) {
        stage.setTitle(TITLE);
        stage.setMinWidth(600);
        stage.setMinHeight(510);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.requestFocus();
        stage.getIcons().add(logos.getLogo512());
    }

}
