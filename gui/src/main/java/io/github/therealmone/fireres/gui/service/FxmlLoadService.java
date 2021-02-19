package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.controller.TopMenuBarController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.top.menu.ExportModalWindowController;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

public interface FxmlLoadService {

    Scene loadMainScene(Stage primaryStage);

    Tab loadSampleTab(SamplesTabPaneController samplesTabPaneController, Object userData);

    Stage loadInterpolationPointModalWindow(FunctionParamsController functionParamsController);

    Stage loadRenameSampleModalWindow(SampleTabController sampleTabController);

    Stage loadAboutProgramModalWindow(TopMenuBarController topMenuBarController);

    Stage loadExportModalWindow(SamplesTabPaneController samplesTabPaneController);

    CheckBox loadSampleExportCheckbox(Sample sample, ExportModalWindowController exportModalWindowController);

}
