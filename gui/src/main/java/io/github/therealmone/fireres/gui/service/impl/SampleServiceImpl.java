package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.service.ElementStorageService;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import io.github.therealmone.fireres.gui.service.SampleService;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import static io.github.therealmone.fireres.gui.service.ElementStorageService.SAMPLES_TAB_PANE;

@Slf4j
public class SampleServiceImpl implements SampleService {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @Inject
    private ElementStorageService elementStorageService;

    @Override
    public void createNewSample() {
        val samplesProperties = generationProperties.getSamples();
        val samplesTabPane = (TabPane) elementStorageService.getFirstById(SAMPLES_TAB_PANE).orElseThrow();

        val newTab = createSampleTab(String.format("Образец №%d", samplesProperties.size() + 1));

        val newSampleProperties = new SampleProperties();

        samplesProperties.add(newSampleProperties);
        newTab.setUserData(newSampleProperties);

        samplesTabPane.getTabs().add(samplesTabPane.getTabs().size() - 1, newTab);
        samplesTabPane.getSelectionModel().select(newTab);
    }

    @Override
    public void closeSample(Tab closedSampleTab) {
        val sampleId = ((SampleProperties) closedSampleTab.getUserData()).getId();
        val samplesTabPane = (TabPane) elementStorageService.getFirstById(SAMPLES_TAB_PANE).orElseThrow();

        if (generationProperties.getSamples().removeIf(sample -> sample.getId().equals(sampleId))) {
            renameSamples(samplesTabPane);
        }

        if (samplesTabPane.getTabs().size() == 2) {
            samplesTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        }
    }

    @SneakyThrows
    private Tab createSampleTab(String tabName) {
        val tab = (Tab) fxmlLoadService.loadSampleTab();

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
