package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public interface SampleService {

    void createNewSample(SamplesTabPaneController samplesTabPaneController);

    void closeSample(TabPane samplesTabPane, Tab closedSampleTab);

    SampleProperties getSampleProperties(Tab sampleTab);

}
