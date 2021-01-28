package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.event.SampleClosedEventHandler;
import io.github.therealmone.fireres.gui.service.SampleCreatorService;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.SneakyThrows;
import lombok.val;

public class SampleCreatorServiceImpl implements SampleCreatorService {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void createNewSample(TabPane samplesTabPane) {
        val samplesProperties = generationProperties.getSamples();

        val newTab = createSampleTab(String.format("Образец №%d", samplesProperties.size() + 1));
        val newSampleProperties = new SampleProperties();

        newTab.setOnClosed(new SampleClosedEventHandler(samplesTabPane, generationProperties));

        samplesProperties.add(newSampleProperties);
        newTab.setUserData(newSampleProperties);

        samplesTabPane.getTabs().add(samplesTabPane.getTabs().size() - 1, newTab);
        samplesTabPane.getSelectionModel().select(newTab);
    }

    @SneakyThrows
    private Tab createSampleTab(String tabName) {
        val tab = (Tab) FXMLLoader.load(getClass().getResource("/component/sample.fxml"));

        tab.setText(tabName);

        return tab;
    }

}
