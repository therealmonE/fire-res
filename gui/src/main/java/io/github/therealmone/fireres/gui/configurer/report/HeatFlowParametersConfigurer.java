package io.github.therealmone.fireres.gui.configurer.report;

import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlow;
import io.github.therealmone.fireres.gui.preset.Preset;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import lombok.val;

public class HeatFlowParametersConfigurer extends AbstractReportParametersConfigurer<HeatFlow> {

    private static final Integer SENSORS_COUNT_MIN = 1;
    private static final Integer SENSORS_COUNT_MAX = 100;

    private static final Double HEAT_FLOW_MIN_BOUND = 0.1;
    private static final Double HEAT_FLOW_MAX_BOUND = 100d;
    private static final Double HEAT_FLOW_BOUND_INCREMENT = 0.1;

    @Override
    public void config(HeatFlow heatFlow, Preset preset) {
        val sampleProperties = heatFlow.getSample().getSampleProperties();
        val presetProperties = preset.getProperties(HeatFlowProperties.class);

        sampleProperties.putReportProperties(presetProperties);

        val heatFlowParameters = heatFlow.getHeatFlowParams();

        resetSensorsCount(heatFlowParameters.getSensors(), presetProperties);
        resetHeatFlowBound(heatFlowParameters.getBound(), presetProperties);

        resetFunctionParameters(heatFlow.getFunctionParams(), presetProperties.getFunctionForm());
    }

    private void resetSensorsCount(Spinner<Integer> sensorsCount, HeatFlowProperties properties) {
        sensorsCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                SENSORS_COUNT_MIN,
                SENSORS_COUNT_MAX,
                properties.getSensorCount()
        ));
    }

    private void resetHeatFlowBound(Spinner<Double> heatFlow, HeatFlowProperties properties) {
        heatFlow.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                HEAT_FLOW_MIN_BOUND,
                HEAT_FLOW_MAX_BOUND,
                properties.getBound(),
                HEAT_FLOW_BOUND_INCREMENT
        ));
    }
}
