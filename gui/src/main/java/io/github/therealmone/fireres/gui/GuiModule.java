package io.github.therealmone.fireres.gui;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.gui.annotation.MainScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.SneakyThrows;
import lombok.val;

public class GuiModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(GraphicalInterface.class);
        bind(Scene.class).annotatedWith(MainScene.class)
                .toInstance(loadMainScene());
    }

    @SneakyThrows
    private Scene loadMainScene() {
        val mainSceneUrl = getClass().getResource("/scene/mainScene.fxml");

        Parent root = FXMLLoader.load(mainSceneUrl);

        return new Scene(root);
    }
}
