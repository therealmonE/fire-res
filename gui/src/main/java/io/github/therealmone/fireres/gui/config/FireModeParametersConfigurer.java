package io.github.therealmone.fireres.gui.config;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.gui.controller.fire.mode.FireModeParamsController;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import lombok.val;

public class FireModeParametersConfigurer implements Configurer<FireModeParamsController> {

    private static final Integer DEFAULT_THERMOCOUPLE_COUNT = 1;
    private static final Integer DEFAULT_THERMOCOUPLE_COUNT_MIN = 1;
    private static final Integer DEFAULT_THERMOCOUPLE_COUNT_MAX = 100;

    @Override
    public void config(FireModeParamsController controller) {
        val sampleProperties = controller.getSample().getSampleProperties();

        resetThermocoupleCount(controller.getThermocoupleSpinner(), sampleProperties);
    }

    private void resetThermocoupleCount(Spinner<Integer> thermocoupleSpinner, SampleProperties sampleProperties) {
        sampleProperties.getReportPropertiesByClass(FireModeProperties.class)
                .orElseThrow()
                .setThermocoupleCount(DEFAULT_THERMOCOUPLE_COUNT);

        thermocoupleSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                DEFAULT_THERMOCOUPLE_COUNT_MIN,
                DEFAULT_THERMOCOUPLE_COUNT_MAX,
                DEFAULT_THERMOCOUPLE_COUNT
        ));
    }
}
