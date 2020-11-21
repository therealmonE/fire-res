package io.github.therealmone.fireres.heatflow.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.heatflow.GuiceRunner;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.heatflow.TestGenerationProperties.BOUND;
import static io.github.therealmone.fireres.heatflow.TestGenerationProperties.TIME;
import static io.github.therealmone.fireres.heatflow.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.heatflow.TestUtils.assertFunctionIsConstant;
import static io.github.therealmone.fireres.heatflow.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.heatflow.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.heatflow.TestUtils.assertThermocouplesTemperaturesEqualsMean;

@RunWith(GuiceRunner.class)
public class HeatFlowReportRepeatingTest {

    private static final Integer CYCLES = 100;

    @Inject
    private HeatFlowReportProvider reportProvider;

    @Test
    public void provideReportTest() {
        for (int i = 0; i < CYCLES; i++) {
            val report = reportProvider.get();

            val bound = report.getSamples().get(0).getBound();

            assertFunctionIsConstant(BOUND, bound.getValue());

            val mean = report.getSamples().get(0).getMeanTemperature();

            assertFunctionConstantlyGrowing(mean.getValue());
            assertFunctionNotLower(mean.getValue(), constantFunction(TIME, 0).getValue());
            assertFunctionNotHigher(mean.getValue(), bound.getValue());

            val sensors = report.getSamples().get(0).getSensorTemperatures();

            assertThermocouplesTemperaturesEqualsMean(sensors, mean);

            sensors.forEach(sensor -> {
                assertFunctionConstantlyGrowing(sensor.getValue());
                assertFunctionNotHigher(sensor.getValue(), bound.getValue());
                assertFunctionNotLower(sensor.getValue(), constantFunction(TIME, 0).getValue());
            });
        }
    }

}
