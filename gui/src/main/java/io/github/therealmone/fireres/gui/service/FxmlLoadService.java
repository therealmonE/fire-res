package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

public interface FxmlLoadService {

    Scene loadMainScene();

    Tab loadSampleTab(SamplesTabPaneController samplesTabPaneController, Object userData);

    Stage loadInterpolationPointModalWindow();

}
