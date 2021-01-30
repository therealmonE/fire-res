package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.github.therealmone.fireres.gui.controller.SampleController;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.SneakyThrows;
import lombok.val;

import java.net.URL;

public class FxmlLoadServiceImpl implements FxmlLoadService {

    @Inject
    private Injector injector;

    @Override
    @SneakyThrows
    public Scene loadMainScene() {
        val mainSceneResource = getClass().getResource("/scene/mainScene.fxml");
        val loader = createLoader(mainSceneResource);
        val root = (Parent) loader.load();

        return new Scene(root);
    }

    @Override
    @SneakyThrows
    public Tab loadSampleTab() {
        val sampleResource = getClass().getResource("/component/sample.fxml");
        val loader = createLoader(sampleResource);

        return loader.load();
    }

    private FXMLLoader createLoader(URL resource) {
        val loader = new FXMLLoader(resource);

        loader.setControllerFactory(injector::getInstance);

        return loader;
    }

}
