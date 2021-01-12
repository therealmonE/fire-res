package io.github.therealmone.fireres.gui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import lombok.val;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        val injector = Guice.createInjector(new GuiModule());
        val gui = injector.getInstance(GraphicalInterface.class);
        val mainScene = loadMainScene(injector);

        gui.start(stage, mainScene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @SneakyThrows
    private Scene loadMainScene(Injector injector) {
        val mainSceneUrl = getClass().getResource("/scene/mainScene.fxml");
        val loader = new FXMLLoader(mainSceneUrl);

        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load();

        return new Scene(root);
    }

}
