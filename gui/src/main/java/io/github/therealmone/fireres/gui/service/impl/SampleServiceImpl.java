package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.config.ExcessPressureProperties;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.gui.controller.common.SampleTab;
import io.github.therealmone.fireres.gui.controller.common.SamplesTabPane;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import io.github.therealmone.fireres.gui.service.SampleService;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SampleServiceImpl implements SampleService {

    public static final String SAMPLE_NAME_TEMPLATE = "Образец №%d";
    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FxmlLoadService fxmlLoadService;

    private final AtomicInteger sampleCounter = new AtomicInteger(0);

    @Override
    public void createNewSample(SamplesTabPane samplesTabPaneController) {
        val samplesProperties = generationProperties.getSamples();
        val samplesTabPane = samplesTabPaneController.getComponent();

        val newSampleProperties = initializeProperties();

        samplesProperties.add(newSampleProperties);

        val sampleName = createSampleName(samplesProperties);
        val newTab = createSampleTab(samplesTabPaneController, newSampleProperties, sampleName);

        newSampleProperties.setName(sampleName);
        samplesTabPane.getTabs().add(samplesTabPane.getTabs().size() - 1, newTab);
        samplesTabPane.getSelectionModel().select(newTab);
    }

    private SampleProperties initializeProperties() {
        val sampleProperties = new SampleProperties();

        sampleProperties.putReportProperties(new FireModeProperties());
        sampleProperties.putReportProperties(new ExcessPressureProperties());
        sampleProperties.putReportProperties(new HeatFlowProperties());
        sampleProperties.putReportProperties(new UnheatedSurfaceProperties());

        return sampleProperties;
    }

    private String createSampleName(List<SampleProperties> samples) {
        var name = String.format(SAMPLE_NAME_TEMPLATE, sampleCounter.incrementAndGet());

        while (sampleNameAlreadyExists(name, samples)) {
            name = String.format(SAMPLE_NAME_TEMPLATE, sampleCounter.incrementAndGet());
        }

        return name;
    }

    private boolean sampleNameAlreadyExists(String name, List<SampleProperties> samples) {
        return samples.stream().anyMatch(sample -> name.equals(sample.getName()));
    }

    @Override
    public void closeSample(SamplesTabPane samplesTabPane, SampleTab closedSampleTab) {
        val sampleId = closedSampleTab.getSample().getId();

        samplesTabPane.getChildren().removeIf(tab -> tab.equals(closedSampleTab));
        generationProperties.getSamples().removeIf(sample -> sample.getId().equals(sampleId));

        if (samplesTabPane.getComponent().getTabs().size() == 2) {
            samplesTabPane.getComponent().setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        }
    }

    @SneakyThrows
    private Tab createSampleTab(SamplesTabPane samplesTabPane,
                                SampleProperties sampleProperties,
                                String tabName) {

        val sample = (SampleTab) fxmlLoadService.loadComponent(
                SampleTab.class,
                samplesTabPane,
                sampleTab -> sampleTab.setSample(new Sample(sampleProperties)));

        sample.getComponent().setText(tabName);

        return sample.getComponent();
    }

}
