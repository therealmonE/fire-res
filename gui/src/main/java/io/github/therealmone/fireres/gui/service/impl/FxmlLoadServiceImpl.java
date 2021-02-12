package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.github.therealmone.fireres.gui.controller.MainSceneController;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.common.InterpolationPointsModalWindowController;
import io.github.therealmone.fireres.gui.controller.common.SampleRenameModalWindowController;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import lombok.val;

import java.net.URL;

public class FxmlLoadServiceImpl implements FxmlLoadService {

    @Inject
    private Injector injector;

    @Override
    @SneakyThrows
    public Scene loadMainScene(Stage primaryStage) {
        val mainSceneResource = getClass().getResource("/scene/mainScene.fxml");
        val loader = createLoader(mainSceneResource);
        val root = (Parent) loader.load();

        ((MainSceneController) loader.getController()).setPrimaryStage(primaryStage);

        return new Scene(root);
    }

    @Override
    @SneakyThrows
    public Tab loadSampleTab(SamplesTabPaneController samplesTabPaneController, Object userData) {
        val sampleResource = getClass().getResource("/component/sample.fxml");
        val loader = createLoader(sampleResource);

        val sampleTab = (Tab) loader.load();
        val controller = (SampleTabController) loader.getController();

        sampleTab.setUserData(userData);
        controller.setSamplesTabPaneController(samplesTabPaneController);
        controller.postConstruct();
        samplesTabPaneController.getSampleTabControllers().add(controller);

        return sampleTab;
    }

    @Override
    @SneakyThrows
    public Stage loadInterpolationPointModalWindow(FunctionParamsController functionParamsController) {
        val resource = getClass().getResource("/component/common-params/interpolationPointsModalWindow.fxml");
        val loader = createLoader(resource);

        val modalWindowPane = (Pane) loader.load();
        val controller = (InterpolationPointsModalWindowController) loader.getController();

        controller.setFunctionParamsController(functionParamsController);

        val modalWindow = new Stage();

        modalWindow.setScene(new Scene(modalWindowPane));
        modalWindow.setTitle("Добавление точек интерполяции");
        modalWindow.setResizable(false);
        modalWindow.initModality(Modality.APPLICATION_MODAL);

        controller.setModalWindow(modalWindow);

        return modalWindow;
    }

    @Override
    @SneakyThrows
    public Stage loadRenameSampleModalWindow(SampleTabController sampleTabController) {
        val renameResource = getClass().getResource("/component/common-params/sampleRenameModalWindow.fxml");
        val loader = createLoader(renameResource);

        val renameModalWindowPane = (Pane) loader.load();
        val controller = (SampleRenameModalWindowController) loader.getController();

        controller.setSampleTabController(sampleTabController);

        val modalWindow = new Stage();

        modalWindow.setScene(new Scene(renameModalWindowPane));
        modalWindow.setTitle("Переимеование образца");
        modalWindow.setResizable(false);
        modalWindow.initModality(Modality.APPLICATION_MODAL);

        controller.setModalWindow(modalWindow);
        controller.postConstruct();

        return modalWindow;
    }

    private FXMLLoader createLoader(URL resource) {
        val loader = new FXMLLoader(resource);

        loader.setControllerFactory(injector::getInstance);

        return loader;
    }

}
