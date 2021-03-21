package io.github.therealmone.fireres.gui.config;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.firemode.model.FireModeType;
import io.github.therealmone.fireres.gui.controller.fire.mode.FireModeParams;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import lombok.val;

public class FireModeParametersConfigurer implements Configurer<FireModeParams> {

    private static final Integer DEFAULT_THERMOCOUPLE_COUNT = 1;
    private static final Integer DEFAULT_THERMOCOUPLE_COUNT_MIN = 1;
    private static final Integer DEFAULT_THERMOCOUPLE_COUNT_MAX = 100;

    private static final FireModeType DEFAULT_FIRE_MODE_TYPE = FireModeType.LOG;

    @Override
    public void config(FireModeParams controller) {
        val sampleProperties = controller.getSample().getSampleProperties();

        resetThermocoupleCount(controller.getThermocouples(), sampleProperties);
        resetFireModeType(controller.getFireModeType(), sampleProperties);
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

    private void resetFireModeType(ChoiceBox<FireModeType> fireModeType, SampleProperties sampleProperties) {
        sampleProperties.getReportPropertiesByClass(FireModeProperties.class)
                .orElseThrow()
                .setFireModeType(DEFAULT_FIRE_MODE_TYPE);

        fireModeType.getSelectionModel().select(FireModeType.LOG);
    }
}
