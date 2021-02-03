package io.github.therealmone.fireres.gui.config;

import io.github.therealmone.fireres.core.config.Interpolation;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import lombok.val;

import java.util.function.Function;

public class FunctionParametersConfigurer implements Configurer<FunctionParamsController> {

    private static final Double DEFAULT_LINEAR_COEFFICIENT = 0.5d;
    private static final Double DEFAULT_LINEAR_COEFFICIENT_MIN = 0d;
    private static final Double DEFAULT_LINEAR_COEFFICIENT_MAX = 1d;
    private static final Double DEFAULT_LINEAR_COEFFICIENT_INCREMENT = 0.01;

    private static final Double DEFAULT_DISPERSION_COEFFICIENT = 1d;
    private static final Double DEFAULT_DISPERSION_COEFFICIENT_MIN = 0d;
    private static final Double DEFAULT_DISPERSION_COEFFICIENT_MAX = 1d;
    private static final Double DEFAULT_DISPERSION_COEFFICIENT_INCREMENT = 0.01;

    @Override
    public void config(FunctionParamsController controller) {
        val sampleProperties = controller.getSample().getSampleProperties();
        val mapper = controller.getSamplePropertiesMapper();

        resetLinearCoefficient(controller.getLinearSpinner(), sampleProperties, mapper);
        resetDispersionCoefficient(controller.getDispersionSpinner(), sampleProperties, mapper);
    }

    private void resetLinearCoefficient(Spinner<Double> LinearCoefficient, SampleProperties sample,
                                        Function<SampleProperties, Interpolation> sampleMapper) {

        sampleMapper.apply(sample).setLinearityCoefficient(DEFAULT_LINEAR_COEFFICIENT);

        LinearCoefficient.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                DEFAULT_LINEAR_COEFFICIENT_MIN,
                DEFAULT_LINEAR_COEFFICIENT_MAX,
                DEFAULT_LINEAR_COEFFICIENT,
                DEFAULT_LINEAR_COEFFICIENT_INCREMENT
        ));
    }

    private void resetDispersionCoefficient(Spinner<Double> DispersionCoefficient, SampleProperties sample,
                                            Function<SampleProperties, Interpolation> sampleMapper) {

        sampleMapper.apply(sample).setDispersionCoefficient(DEFAULT_DISPERSION_COEFFICIENT);

        DispersionCoefficient.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                DEFAULT_DISPERSION_COEFFICIENT_MIN,
                DEFAULT_DISPERSION_COEFFICIENT_MAX,
                DEFAULT_DISPERSION_COEFFICIENT,
                DEFAULT_DISPERSION_COEFFICIENT_INCREMENT
        ));
    }
}
