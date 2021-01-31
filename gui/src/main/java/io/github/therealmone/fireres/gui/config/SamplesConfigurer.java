package io.github.therealmone.fireres.gui.config;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.service.SampleService;
import lombok.val;

public class SamplesConfigurer implements Configurer<SamplesTabPaneController> {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private SampleService sampleService;

    @Override
    public void config(SamplesTabPaneController samplesTabPaneController) {
        val samplesTabPane = samplesTabPaneController.getSamplesTabPane();

        generationProperties.getSamples().clear();
        samplesTabPane.getTabs().removeIf(tab -> !tab.getId().equals("addSampleTab"));
        sampleService.createNewSample(samplesTabPaneController);
    }
}
