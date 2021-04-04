package io.github.therealmone.fireres.gui.configurer;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.gui.controller.common.GeneralParams;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

public class GeneralParametersConfigurer implements Configurer<GeneralParams> {

    @Inject
    private GenerationProperties generationProperties;

    private static final Integer DEFAULT_ENV_TEMPERATURE = 21;
    private static final Integer DEFAULT_ENV_TEMPERATURE_MIN = 0;
    private static final Integer DEFAULT_ENV_TEMPERATURE_MAX = 100;

    private static final Integer DEFAULT_TIME = 50;
    private static final Integer DEFAULT_TIME_MIN = 1;
    private static final Integer DEFAULT_TIME_MAX = 1000;

    @Override
    public void config(GeneralParams generalParams) {
        resetTime(generalParams.getTime());
        resetEnvironmentTemperature(generalParams.getEnvironmentTemperature());
    }

    private void resetEnvironmentTemperature(Spinner<Integer> environmentTemperature) {
        generationProperties.getGeneral().setEnvironmentTemperature(DEFAULT_ENV_TEMPERATURE);

        environmentTemperature.setValueFactory(new IntegerSpinnerValueFactory(
                DEFAULT_ENV_TEMPERATURE_MIN,
                DEFAULT_ENV_TEMPERATURE_MAX,
                DEFAULT_ENV_TEMPERATURE));
    }

    private void resetTime(Spinner<Integer> time) {
        generationProperties.getGeneral().setTime(DEFAULT_TIME + 1);

        time.setValueFactory(new IntegerSpinnerValueFactory(
                DEFAULT_TIME_MIN,
                DEFAULT_TIME_MAX,
                DEFAULT_TIME));
    }

}
