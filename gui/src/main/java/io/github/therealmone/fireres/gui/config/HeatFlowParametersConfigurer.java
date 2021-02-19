package io.github.therealmone.fireres.gui.config;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlowParamsController;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import lombok.val;

public class HeatFlowParametersConfigurer implements Configurer<HeatFlowParamsController> {

    private static final Integer DEFAULT_SENSORS_COUNT = 1;
    private static final Integer DEFAULT_SENSORS_COUNT_MIN = 1;
    private static final Integer DEFAULT_SENSORS_COUNT_MAX = 100;

    private static final Double DEFAULT_HEAT_FLOW_BOUND = 3.5;
    private static final Double DEFAULT_HEAT_FLOW_MIN_BOUND = 0.1;
    private static final Double DEFAULT_HEAT_FLOW_MAX_BOUND = 100d;
    private static final Double DEFAULT_HEAT_FLOW_BOUND_INCREMENT = 0.1;

    @Override
    public void config(HeatFlowParamsController controller) {
        val sampleProperties = controller.getSample().getSampleProperties();

        resetSensorsCount(controller.getSensorSpinner(), sampleProperties);
        resetHeatFlowBound(controller.getHeatFlowBoundSpinner(), sampleProperties);
    }

    private void resetSensorsCount(Spinner<Integer> sensorsCount, SampleProperties sample) {
        sample.getReportPropertiesByClass(HeatFlowProperties.class)
                .orElseThrow()
                .setSensorCount(DEFAULT_SENSORS_COUNT);

        sensorsCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                DEFAULT_SENSORS_COUNT_MIN,
                DEFAULT_SENSORS_COUNT_MAX,
                DEFAULT_SENSORS_COUNT
        ));
    }

    private void resetHeatFlowBound(Spinner<Double> heatFlow, SampleProperties sample) {
        sample.getReportPropertiesByClass(HeatFlowProperties.class)
                .orElseThrow()
                .setBound(DEFAULT_HEAT_FLOW_BOUND);

        heatFlow.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                DEFAULT_HEAT_FLOW_MIN_BOUND,
                DEFAULT_HEAT_FLOW_MAX_BOUND,
                DEFAULT_HEAT_FLOW_BOUND,
                DEFAULT_HEAT_FLOW_BOUND_INCREMENT
        ));
    }
}
