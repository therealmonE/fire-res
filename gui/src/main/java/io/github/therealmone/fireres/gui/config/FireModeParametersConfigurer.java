package io.github.therealmone.fireres.gui.config;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.firemode.model.FireModeType;
import io.github.therealmone.fireres.gui.controller.fire.mode.FireModeParams;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import lombok.val;

public class FireModeParametersConfigurer implements Configurer<FireModeParams> {

    private static final Integer DEFAULT_THERMOCOUPLE_COUNT = 1;
    private static final Integer DEFAULT_THERMOCOUPLE_COUNT_MIN = 1;
    private static final Integer DEFAULT_THERMOCOUPLE_COUNT_MAX = 100;

    private static final FireModeType DEFAULT_FIRE_MODE_TYPE = FireModeType.LOG;

    private static final Integer TEMPERATURE_MAINTAINING_MIN = 0;
    private static final Integer TEMPERATURE_MAINTAINING_MAX = Integer.MAX_VALUE;

    private static final Boolean SHOW_BOUNDS_DEFAULT = true;
    private static final Boolean SHOW_MEAN_TEMPERATURE_DEFAULT = true;

    @Override
    public void config(FireModeParams controller) {
        val sampleProperties = controller.getSample().getSampleProperties();

        resetThermocoupleCount(controller.getThermocouples(), sampleProperties);
        resetFireModeType(controller.getFireModeType(), sampleProperties);
        resetStandardTemperatureMaintaining(controller.getTemperatureMaintaining());
        resetShowBounds(controller.getShowBounds(), sampleProperties);
        resetShowMeanTemperature(controller.getShowMeanTemperature(), sampleProperties);
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

    private void resetStandardTemperatureMaintaining(Spinner<Integer> standardTemperatureMaintaining) {
        standardTemperatureMaintaining.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                TEMPERATURE_MAINTAINING_MIN,
                TEMPERATURE_MAINTAINING_MAX,
                TEMPERATURE_MAINTAINING_MIN));
    }

    private void resetShowBounds(CheckBox showBounds, SampleProperties sampleProperties) {
        sampleProperties.getReportPropertiesByClass(FireModeProperties.class)
                .orElseThrow()
                .setShowBounds(SHOW_BOUNDS_DEFAULT);

        showBounds.setSelected(SHOW_BOUNDS_DEFAULT);
    }

    private void resetShowMeanTemperature(CheckBox showMeanTemperature, SampleProperties sampleProperties) {
        sampleProperties.getReportPropertiesByClass(FireModeProperties.class)
                .orElseThrow()
                .setShowMeanTemperature(SHOW_MEAN_TEMPERATURE_DEFAULT);

        showMeanTemperature.setSelected(SHOW_MEAN_TEMPERATURE_DEFAULT);
    }
}
