package io.github.therealmone.fireres.gui.config.thermocouple.groups;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.config.Configurer;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third.ThirdGroupParamsController;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import lombok.val;

public class ThirdThermocoupleGroupParametersConfigurer implements Configurer<ThirdGroupParamsController> {

    private static final Integer DEFAULT_THERMOCOUPLES_NUMBER = 1;
    private static final Integer DEFAULT_THERMOCOUPLES_NUMBER_MIN = 1;
    private static final Integer DEFAULT_THERMOCOUPLES_NUMBER_MAX = 100;

    private static final Integer DEFAULT_BOUND = 300;
    private static final Integer DEFAULT_BOUND_MIN = 2;
    private static final Integer DEFAULT_BOUND_MAX = 10000;
    private static final Integer DEFAULT_BOUND_INCREMENT = 100;

    @Override
    public void config(ThirdGroupParamsController controller) {
        val sampleProperties = controller.getSample().getSampleProperties();

        resetThermocouplesNumber(controller.getThirdGroupThermocouplesCountSpinner(), sampleProperties);
        resetBound(controller.getThirdGroupBoundSpinner(), sampleProperties);
    }

    private void resetThermocouplesNumber(Spinner<Integer> ThermocouplesNumber, SampleProperties sample) {
        sample.getReportPropertiesByClass(UnheatedSurfaceProperties.class)
                .orElseThrow()
                .getThirdGroup()
                .setThermocoupleCount(DEFAULT_THERMOCOUPLES_NUMBER);

        ThermocouplesNumber.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                DEFAULT_THERMOCOUPLES_NUMBER_MIN,
                DEFAULT_THERMOCOUPLES_NUMBER_MAX,
                DEFAULT_THERMOCOUPLES_NUMBER
        ));
    }

    private void resetBound(Spinner<Integer> ThermocouplesBound, SampleProperties sample) {
        sample.getReportPropertiesByClass(UnheatedSurfaceProperties.class)
                .orElseThrow()
                .getThirdGroup()
                .setBound(DEFAULT_BOUND);

        ThermocouplesBound.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                DEFAULT_BOUND_MIN,
                DEFAULT_BOUND_MAX,
                DEFAULT_BOUND,
                DEFAULT_BOUND_INCREMENT
        ));
    }
}
