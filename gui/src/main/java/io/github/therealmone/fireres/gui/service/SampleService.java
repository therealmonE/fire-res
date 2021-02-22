package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.controller.common.SampleTab;
import io.github.therealmone.fireres.gui.controller.common.SamplesTabPane;

public interface SampleService {

    void createNewSample(SamplesTabPane samplesTabPane);

    void closeSample(SamplesTabPane samplesTabPane, SampleTab closedSampleTab);

}
