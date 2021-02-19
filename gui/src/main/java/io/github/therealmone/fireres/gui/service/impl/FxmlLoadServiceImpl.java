package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.MainSceneController;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.controller.TopMenuBarController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.common.InterpolationPointsModalWindowController;
import io.github.therealmone.fireres.gui.controller.common.SampleRenameModalWindowController;
import io.github.therealmone.fireres.gui.controller.top.menu.AboutProgramController;
import io.github.therealmone.fireres.gui.controller.top.menu.ExportModalWindowController;
import io.github.therealmone.fireres.gui.controller.top.menu.SampleExportCheckboxController;
import io.github.therealmone.fireres.gui.model.Logos;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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

    @Inject
    private Logos logos;

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

        modalWindow.getIcons().add(logos.getLogo512());

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
        modalWindow.setTitle("Переименование образца");
        modalWindow.setResizable(false);
        modalWindow.initModality(Modality.APPLICATION_MODAL);

        controller.setModalWindow(modalWindow);
        controller.postConstruct();

        modalWindow.getIcons().add(logos.getLogo512());

        return modalWindow;
    }

    @Override
    @SneakyThrows
    public Stage loadAboutProgramModalWindow(TopMenuBarController topMenuBarController) {
        val aboutProgramResource = getClass().getResource("/component/top-menu/aboutProgram.fxml");
        val loader = createLoader(aboutProgramResource);

        val aboutProgramModalWindowPane = (Pane) loader.load();
        val controller = (AboutProgramController) loader.getController();

        controller.setTopMenuBarController(topMenuBarController);

        val modalWindow = new Stage();

        modalWindow.setScene(new Scene(aboutProgramModalWindowPane));
        modalWindow.setTitle("О программе");
        modalWindow.setResizable(false);
        modalWindow.initModality(Modality.APPLICATION_MODAL);

        controller.setModalWindow(modalWindow);

        modalWindow.getIcons().add(logos.getLogo512());

        return modalWindow;
    }

    @Override
    @SneakyThrows
    public Stage loadExportModalWindow(SamplesTabPaneController samplesTabPaneController) {
        val exportResource = getClass().getResource("/component/top-menu/exportModalWindow.fxml");
        val loader = createLoader(exportResource);

        val exportModalWindowPane = (Pane) loader.load();

        val controller = (ExportModalWindowController) loader.getController();

        controller.setSamplesTabPaneController(samplesTabPaneController);
        controller.postConstruct();

        val modalWindow = new Stage();

        modalWindow.setScene(new Scene(exportModalWindowPane));
        modalWindow.setTitle("Экспорт");
        modalWindow.setResizable(false);
        modalWindow.initModality(Modality.APPLICATION_MODAL);

        modalWindow.getIcons().add(logos.getLogo512());

        return modalWindow;
    }

    @Override
    @SneakyThrows
    public CheckBox loadSampleExportCheckbox(Sample sample, ExportModalWindowController exportModalWindowController) {
        val checkboxResource = getClass().getResource("/component/top-menu/sampleExportComponent.fxml");
        val loader = createLoader(checkboxResource);

        val checkbox = (CheckBox) loader.load();
        val controller = (SampleExportCheckboxController) loader.getController();

        controller.setSample(sample);
        controller.setExportModalWindowController(exportModalWindowController);
        controller.postConstruct();

        exportModalWindowController.getExportCheckboxControllers().add(controller);

        return checkbox;
    }

    private FXMLLoader createLoader(URL resource) {
        val loader = new FXMLLoader(resource);

        loader.setControllerFactory(injector::getInstance);

        return loader;
    }

}
