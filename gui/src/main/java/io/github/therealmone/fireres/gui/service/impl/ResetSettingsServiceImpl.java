package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.gui.service.SampleService;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;

public class ResetSettingsServiceImpl implements ResetSettingsService {

    private static final Integer DEFAULT_ENV_TEMPERATURE = 21;
    private static final Integer DEFAULT_ENV_TEMPERATURE_MIN = 1;
    private static final Integer DEFAULT_ENV_TEMPERATURE_MAX = 40;

    private static final Integer DEFAULT_TIME = 50;
    private static final Integer DEFAULT_TIME_MIN = 1;
    private static final Integer DEFAULT_TIME_MAX = 1000;

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private SampleService sampleService;

    @Override
    public void resetSamples(TabPane samplesTabPane) {
        generationProperties.getSamples().clear();
        samplesTabPane.getTabs().removeIf(tab -> !tab.getId().equals("addSampleTab"));
        sampleService.createNewSample();
    }

    @Override
    public void resetEnvironmentTemperature(Spinner<Integer> environmentTemperatureSpinner) {
        generationProperties.getGeneral().setEnvironmentTemperature(DEFAULT_ENV_TEMPERATURE);
        environmentTemperatureSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(DEFAULT_ENV_TEMPERATURE_MIN, DEFAULT_ENV_TEMPERATURE_MAX));
        environmentTemperatureSpinner.getValueFactory().setValue(DEFAULT_ENV_TEMPERATURE);
    }

    @Override
    public void resetTime(Spinner<Integer> timeSpinner) {
        generationProperties.getGeneral().setTime(DEFAULT_TIME);
        timeSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(DEFAULT_TIME_MIN, DEFAULT_TIME_MAX));
        timeSpinner.getValueFactory().setValue(DEFAULT_TIME);
    }

}
