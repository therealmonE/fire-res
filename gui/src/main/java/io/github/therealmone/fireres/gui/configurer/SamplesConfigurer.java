package io.github.therealmone.fireres.gui.configurer;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.gui.controller.common.SamplesTabPane;
import io.github.therealmone.fireres.gui.service.SampleService;
import lombok.val;

public class SamplesConfigurer implements Configurer<SamplesTabPane> {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private SampleService sampleService;

    @Override
    public void config(SamplesTabPane samplesTabPaneController) {
        val samplesTabPane = samplesTabPaneController.getComponent();

        generationProperties.getSamples().clear();
        samplesTabPane.getTabs().removeIf(tab -> !tab.getId().equals("addSampleTab"));
        sampleService.createNewSample(samplesTabPaneController);
    }
}
