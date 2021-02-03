package io.github.therealmone.fireres.gui.config;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlowParamsController;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import lombok.val;

public class HeatFlowParametersConfigurer implements Configurer<HeatFlowParamsController> {

    private static final Integer DEFAULT_SENSORS_COUNT = 1;
    private static final Integer DEFAULT_SENSORS_COUNT_MIN = 1;
    private static final Integer DEFAULT_SENSORS_COUNT_MAX = 100;

    private static final Integer DEFAULT_HEAT_FLOW = 1;
    private static final Integer DEFAULT_HEAT_FLOW_MIN = 1;
    private static final Integer DEFAULT_HEAT_FLOW_MAX = 10000;
    private static final Integer DEFAULT_HEAT_FLOW_INCREMENT = 100;

    @Override
    public void config(HeatFlowParamsController controller) {
        val sampleProperties = controller.getSample().getSampleProperties();

        resetSensorsCount(controller.getSensorSpinner(), sampleProperties);
        resetHeatFlow(controller.getHeatFlowSpinner(), sampleProperties);
    }

    private void resetSensorsCount(Spinner<Double> SensorsCount, SampleProperties sample) {
        sample.getHeatFlow().setSensorCount(DEFAULT_SENSORS_COUNT);

        SensorsCount.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                DEFAULT_SENSORS_COUNT_MIN,
                DEFAULT_SENSORS_COUNT_MAX,
                DEFAULT_SENSORS_COUNT
        ));
    }

    private void resetHeatFlow(Spinner<Double> heatFlow, SampleProperties sample) {
        sample.getHeatFlow().setBound(DEFAULT_HEAT_FLOW);

        heatFlow.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                DEFAULT_HEAT_FLOW_MIN,
                DEFAULT_HEAT_FLOW_MAX,
                DEFAULT_HEAT_FLOW,
                DEFAULT_HEAT_FLOW_INCREMENT
        ));
    }
}
