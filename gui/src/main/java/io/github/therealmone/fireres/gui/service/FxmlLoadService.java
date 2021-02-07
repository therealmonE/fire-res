package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

public interface FxmlLoadService {

    Scene loadMainScene(Stage primaryStage);

    Tab loadSampleTab(SamplesTabPaneController samplesTabPaneController, Object userData);

    Stage loadInterpolationPointModalWindow(FunctionParamsController functionParamsController);

}
