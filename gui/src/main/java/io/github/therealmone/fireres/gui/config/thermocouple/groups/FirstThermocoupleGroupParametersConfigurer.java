package io.github.therealmone.fireres.gui.config.thermocouple.groups;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.config.Configurer;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first.FirstGroupParamsController;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import lombok.val;

public class FirstThermocoupleGroupParametersConfigurer implements Configurer<FirstGroupParamsController> {
    private static final Integer DEFAULT_THERMOCOUPLES_NUMBER = 1;
    private static final Integer DEFAULT_THERMOCOUPLES_NUMBER_MIN = 1;
    private static final Integer DEFAULT_THERMOCOUPLES_NUMBER_MAX = 100;

    @Override
    public void config(FirstGroupParamsController controller) {
        val sampleProperties = controller.getSample().getSampleProperties();

        resetThermocouplesNumber(controller.getFirstGroupThermocouplesCountSpinner(), sampleProperties);
    }

    private void resetThermocouplesNumber(Spinner<Integer> ThermocouplesNumber, SampleProperties sample) {
        sample.getReportPropertiesByClass(UnheatedSurfaceProperties.class)
                .orElseThrow()
                .getFirstGroup()
                .setThermocoupleCount(DEFAULT_THERMOCOUPLES_NUMBER);

        ThermocouplesNumber.setValueFactory(new IntegerSpinnerValueFactory(
                DEFAULT_THERMOCOUPLES_NUMBER_MIN,
                DEFAULT_THERMOCOUPLES_NUMBER_MAX,
                DEFAULT_THERMOCOUPLES_NUMBER
        ));
    }

}
