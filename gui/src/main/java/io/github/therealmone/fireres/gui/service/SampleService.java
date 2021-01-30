package io.github.therealmone.fireres.gui.service;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public interface SampleService {

    void createNewSample(TabPane samplesTabPane);

    void closeSample(TabPane samplesTabPane, Tab closedSampleTab);

}
