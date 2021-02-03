package io.github.therealmone.fireres.gui.config.thermocouple.groups;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.config.Configurer;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.third.thermocouple.group.ThirdThermocoupleGroupParamsController;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import lombok.val;

public class ThirdThermocoupleGroupParametersConfigurer implements Configurer<ThirdThermocoupleGroupParamsController> {

    private static final Integer DEFAULT_THERMOCOUPLES_NUMBER = 1;
    private static final Integer DEFAULT_THERMOCOUPLES_NUMBER_MIN = 1;
    private static final Integer DEFAULT_THERMOCOUPLES_NUMBER_MAX = 100;

    private static final Integer DEFAULT_BOUND = 300;
    private static final Integer DEFAULT_BOUND_MIN = 1;
    private static final Integer DEFAULT_BOUND_MAX = 10000;
    private static final Integer DEFAULT_BOUND_INCREMENT = 100;

    @Override
    public void config(ThirdThermocoupleGroupParamsController controller) {
        val sampleProperties = controller.getSampleProperties();

        resetThermocouplesNumber(controller.getThirdThermocoupleGroupNumberOfThermocouplesSpinner(), sampleProperties);
        resetBound(controller.getThirdThermocoupleGroupBoundSpinner(), sampleProperties);
    }

    private void resetThermocouplesNumber(Spinner<Integer> ThermocouplesNumber, SampleProperties sample) {
        sample.getUnheatedSurface().getThirdGroup().setThermocoupleCount(DEFAULT_THERMOCOUPLES_NUMBER);

        ThermocouplesNumber.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                DEFAULT_THERMOCOUPLES_NUMBER_MIN,
                DEFAULT_THERMOCOUPLES_NUMBER_MAX,
                DEFAULT_THERMOCOUPLES_NUMBER
        ));
    }

    private void resetBound(Spinner<Integer> ThermocouplesBound, SampleProperties sample) {
        sample.getUnheatedSurface().getThirdGroup().setBound(DEFAULT_THERMOCOUPLES_NUMBER);

        ThermocouplesBound.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                DEFAULT_BOUND_MIN,
                DEFAULT_BOUND_MAX,
                DEFAULT_BOUND,
                DEFAULT_BOUND_INCREMENT
        ));
    }
}
