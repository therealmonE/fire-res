package io.github.therealmone.fireres.gui.config;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.gui.controller.GeneralParamsController;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

public class GeneralParametersConfigurer implements Configurer<GeneralParamsController> {

    @Inject
    private GenerationProperties generationProperties;

    private static final Integer DEFAULT_ENV_TEMPERATURE = 21;
    private static final Integer DEFAULT_ENV_TEMPERATURE_MIN = 15;
    private static final Integer DEFAULT_ENV_TEMPERATURE_MAX = 40;

    private static final Integer DEFAULT_TIME = 50;
    private static final Integer DEFAULT_TIME_MIN = 1;
    private static final Integer DEFAULT_TIME_MAX = 1000;

    @Override
    public void config(GeneralParamsController controller) {
        resetTime(controller.getTimeSpinner());
        resetEnvironmentTemperature(controller.getEnvironmentTemperatureSpinner());
    }

    private void resetEnvironmentTemperature(Spinner<Integer> environmentTemperature) {
        generationProperties.getGeneral().setEnvironmentTemperature(DEFAULT_ENV_TEMPERATURE);

        environmentTemperature.setValueFactory(new IntegerSpinnerValueFactory(
                DEFAULT_ENV_TEMPERATURE_MIN,
                DEFAULT_ENV_TEMPERATURE_MAX,
                DEFAULT_ENV_TEMPERATURE));
    }

    private void resetTime(Spinner<Integer> time) {
        generationProperties.getGeneral().setTime(DEFAULT_TIME);

        time.setValueFactory(new IntegerSpinnerValueFactory(
                DEFAULT_TIME_MIN,
                DEFAULT_TIME_MAX,
                DEFAULT_TIME));
    }

}
