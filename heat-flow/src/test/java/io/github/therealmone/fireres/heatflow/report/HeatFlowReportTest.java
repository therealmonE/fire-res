package io.github.therealmone.fireres.heatflow.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.heatflow.GuiceRunner;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.heatflow.TestGenerationProperties.BOUND;
import static io.github.therealmone.fireres.heatflow.TestGenerationProperties.TIME;
import static io.github.therealmone.fireres.heatflow.TestUtils.*;

@RunWith(GuiceRunner.class)
public class HeatFlowReportTest {

    @Inject
    private HeatFlowReportProvider reportProvider;

    @Test
    public void generateBound() {
        val report = reportProvider.get();

        val bound = report.getSamples().get(0).getBound();

        assertFunctionIsConstant(BOUND, bound.getValue());
    }

    @Test
    public void generateMeanFunction() {
        val report = reportProvider.get();

        val bound = report.getSamples().get(0).getBound();
        val mean = report.getSamples().get(0).getMeanTemperature();

        assertFunctionConstantlyGrowing(mean.getValue());
        assertFunctionNotLower(mean.getValue(), constantFunction(TIME, 0).getValue());
        assertFunctionNotHigher(mean.getValue(), bound.getValue());
    }

    @Test
    public void generateSensorsFunctions() {
        val report = reportProvider.get();

        val bound = report.getSamples().get(0).getBound();
        val mean = report.getSamples().get(0).getMeanTemperature();
        val sensors = report.getSamples().get(0).getSensorTemperatures();

        assertThermocouplesTemperaturesEqualsMean(sensors, mean);

        sensors.forEach(sensor -> {
            assertFunctionConstantlyGrowing(sensor.getValue());
            assertFunctionNotHigher(sensor.getValue(), bound.getValue());
            assertFunctionNotLower(sensor.getValue(), constantFunction(TIME, 0).getValue());
        });
    }

}