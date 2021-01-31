package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import io.github.therealmone.fireres.gui.service.SampleService;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class SampleServiceImpl implements SampleService {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @Override
    public void createNewSample(SamplesTabPaneController samplesTabPaneController) {
        val samplesProperties = generationProperties.getSamples();
        val samplesTabPane = samplesTabPaneController.getSamplesTabPane();

        val newSampleProperties = new SampleProperties();
        val newTab = createSampleTab(
                samplesTabPaneController,
                newSampleProperties,
                String.format("Образец №%d", samplesProperties.size() + 1));

        samplesProperties.add(newSampleProperties);
        newTab.setUserData(newSampleProperties);

        samplesTabPane.getTabs().add(samplesTabPane.getTabs().size() - 1, newTab);
        samplesTabPane.getSelectionModel().select(newTab);
    }

    @Override
    public void closeSample(TabPane samplesTabPane, Tab closedSampleTab) {
        val sampleId = ((SampleProperties) closedSampleTab.getUserData()).getId();

        if (generationProperties.getSamples().removeIf(sample -> sample.getId().equals(sampleId))) {
            renameSamples(samplesTabPane);
        }

        if (samplesTabPane.getTabs().size() == 2) {
            samplesTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        }
    }

    @Override
    public SampleProperties getSampleProperties(Tab sampleTab) {
        if (sampleTab.getUserData() != null && sampleTab.getUserData() instanceof SampleProperties) {
            return (SampleProperties) sampleTab.getUserData();
        } else {
            throw new IllegalStateException("Sample tab user data is not instance of " + SampleProperties.class.getSimpleName());
        }
    }

    @SneakyThrows
    private Tab createSampleTab(SamplesTabPaneController samplesTabPaneController,
                                SampleProperties sampleProperties,
                                String tabName) {

        val tab = (Tab) fxmlLoadService.loadSampleTab(samplesTabPaneController, sampleProperties);

        tab.setText(tabName);

        return tab;
    }

    private void renameSamples(TabPane samplesTabPane) {
        val samplesTabs = samplesTabPane.getTabs();

        for (int i = 0; i < generationProperties.getSamples().size(); i++) {
            val sampleProperties = generationProperties.getSamples().get(i);
            val sampleTab = samplesTabs.stream()
                    .filter(tab ->
                            ((SampleProperties) tab.getUserData()).getId().equals(sampleProperties.getId()))
                    .findFirst()
                    .orElseThrow();

            sampleTab.setText("Образец №" + (i + 1));
        }

        samplesTabs.sort((t1, t2) -> {
            if (t1.getId() != null && t1.getId().equals("addSampleTab")) {
                return 1;
            } else if (t2.getId() != null && t2.getId().equals("addSampleTab")) {
                return -1;
            } else {
                return t1.getText().compareTo(t2.getText());
            }
        });
    }

}
