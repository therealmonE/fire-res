package io.github.therealmone.fireres.gui.config;

import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.gui.controller.common.FunctionParams;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import lombok.val;

import java.util.function.Function;

public class FunctionParametersConfigurer implements Configurer<FunctionParams> {

    private static final Double DEFAULT_COEFFICIENT_MIN = 0d;
    private static final Double DEFAULT_COEFFICIENT_MAX = 1d;
    private static final Double DEFAULT_COEFFICIENT_INCREMENT = 0.01;

    private static final Double DEFAULT_LINEAR_COEFFICIENT = 1d;
    private static final Double DEFAULT_DISPERSION_COEFFICIENT = 0d;
    private static final Double DEFAULT_CHILD_FUNCTIONS_DELTA_COEFFICIENT = 0.25;

    @Override
    public void config(FunctionParams functionParams) {
        val sampleProperties = functionParams.getSample().getSampleProperties();
        val mapper = functionParams.getPropertiesMapper();

        resetLinearCoefficient(functionParams.getLinearityCoefficient(), sampleProperties, mapper);
        resetDispersionCoefficient(functionParams.getDispersionCoefficient(), sampleProperties, mapper);
        resetChildFunctionsDeltaCoefficient(functionParams.getChildFunctionsDeltaCoefficient(), sampleProperties, mapper);
        resetInterpolationPoints(functionParams.getInterpolationPoints(), sampleProperties, mapper);
    }

    private void resetLinearCoefficient(Spinner<Double> linearCoefficient, SampleProperties sample,
                                        Function<SampleProperties, FunctionForm> sampleMapper) {

        sampleMapper.apply(sample).setLinearityCoefficient(DEFAULT_LINEAR_COEFFICIENT);

        linearCoefficient.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                DEFAULT_COEFFICIENT_MIN,
                DEFAULT_COEFFICIENT_MAX,
                DEFAULT_LINEAR_COEFFICIENT,
                DEFAULT_COEFFICIENT_INCREMENT
        ));
    }

    private void resetDispersionCoefficient(Spinner<Double> dispersionCoefficient, SampleProperties sample,
                                            Function<SampleProperties, FunctionForm> sampleMapper) {

        sampleMapper.apply(sample).setDispersionCoefficient(DEFAULT_DISPERSION_COEFFICIENT);

        dispersionCoefficient.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                DEFAULT_COEFFICIENT_MIN,
                DEFAULT_COEFFICIENT_MAX,
                DEFAULT_DISPERSION_COEFFICIENT,
                DEFAULT_COEFFICIENT_INCREMENT
        ));
    }

    private void resetChildFunctionsDeltaCoefficient(Spinner<Double> childFunctionsDeltaCoefficient,
                                                     SampleProperties sample,
                                                     Function<SampleProperties, FunctionForm> sampleMapper) {

        sampleMapper.apply(sample).setChildFunctionsDeltaCoefficient(DEFAULT_CHILD_FUNCTIONS_DELTA_COEFFICIENT);

        childFunctionsDeltaCoefficient.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                DEFAULT_COEFFICIENT_MIN,
                DEFAULT_COEFFICIENT_MAX,
                DEFAULT_CHILD_FUNCTIONS_DELTA_COEFFICIENT,
                DEFAULT_COEFFICIENT_INCREMENT
        ));
    }

    private void resetInterpolationPoints(TableView<Point<?>> interpolationPoints, SampleProperties sample,
                                          Function<SampleProperties, FunctionForm> sampleMapper) {

        interpolationPoints.getItems().clear();
        sampleMapper.apply(sample).getInterpolationPoints().clear();
    }
}
