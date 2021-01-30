package io.github.therealmone.fireres.gui.service;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public interface FxmlLoadService {

    Scene loadMainScene();

    Tab loadSampleTab(TabPane samplesTabPane);

}
