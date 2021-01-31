package io.github.therealmone.fireres.gui.config;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressureParamsController;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import lombok.val;

public class ExcessPressureParametersConfigurer implements Configurer<ExcessPressureParamsController> {

    private static final Double DEFAULT_BASE_PRESSURE = 10d;
    private static final Double DEFAULT_BASE_PRESSURE_MIN = 0d;
    private static final Double DEFAULT_BASE_PRESSURE_MAX = 1000d;

    private static final Double DEFAULT_DISPERSION_COEFFICIENT = 0.98;
    private static final Double DEFAULT_DISPERSION_COEFFICIENT_MIN = 0d;
    private static final Double DEFAULT_DISPERSION_COEFFICIENT_MAX = 1d;
    private static final Double DEFAULT_DISPERSION_COEFFICIENT_INCREMENT = 0.01;

    private static final Double DEFAULT_DELTA = 2d;
    private static final Double DEFAULT_DELTA_MIN = 0d;
    private static final Double DEFAULT_DELTA_MAX = 1000d;

    @Override
    public void config(ExcessPressureParamsController controller) {
        val sampleProperties = controller.getSampleProperties();

        resetBasePressure(controller.getBasePressureSpinner(), sampleProperties);
        resetDispersionCoefficient(controller.getDispersionCoefficientSpinner(), sampleProperties);
        resetDelta(controller.getDeltaSpinner(), sampleProperties);
    }

    private void resetBasePressure(Spinner<Double> basePressure, SampleProperties sample) {
        sample.getExcessPressure().setBasePressure(DEFAULT_BASE_PRESSURE);

        basePressure.setValueFactory(new DoubleSpinnerValueFactory(
                DEFAULT_BASE_PRESSURE_MIN,
                DEFAULT_BASE_PRESSURE_MAX,
                DEFAULT_BASE_PRESSURE
        ));
    }

    private void resetDispersionCoefficient(Spinner<Double> dispersionCoefficient, SampleProperties sample) {
        sample.getExcessPressure().setDispersionCoefficient(DEFAULT_DISPERSION_COEFFICIENT);

        dispersionCoefficient.setValueFactory(new DoubleSpinnerValueFactory(
                DEFAULT_DISPERSION_COEFFICIENT_MIN,
                DEFAULT_DISPERSION_COEFFICIENT_MAX,
                DEFAULT_DISPERSION_COEFFICIENT,
                DEFAULT_DISPERSION_COEFFICIENT_INCREMENT
        ));
    }

    private void resetDelta(Spinner<Double> delta, SampleProperties sample) {
        sample.getExcessPressure().setDelta(DEFAULT_DELTA);

        delta.setValueFactory(new DoubleSpinnerValueFactory(
                DEFAULT_DELTA_MIN,
                DEFAULT_DELTA_MAX,
                DEFAULT_DELTA
        ));
    }

}
