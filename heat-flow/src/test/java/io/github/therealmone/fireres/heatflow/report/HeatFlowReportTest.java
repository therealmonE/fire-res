package io.github.therealmone.fireres.heatflow.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.heatflow.HeatFlowGuiceRunner;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionIsConstant;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.heatflow.TestGenerationProperties.BOUND;
import static io.github.therealmone.fireres.heatflow.TestGenerationProperties.TIME;

@RunWith(HeatFlowGuiceRunner.class)
public class HeatFlowReportTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private HeatFlowService heatFlowService;

    @Test
    public void generateBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = heatFlowService.createReport(sample);

        val bound = report.getBound();

        assertFunctionIsConstant(BOUND, bound.getValue());
    }

    @Test
    public void generateMeanFunction() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = heatFlowService.createReport(sample);

        val bound = report.getBound();
        val mean = report.getMeanTemperature();

        assertFunctionConstantlyGrowing(mean.getValue());
        assertFunctionNotLower(mean.getValue(), constantFunction(TIME, 0).getValue());
        assertFunctionNotHigher(mean.getValue(), bound.getValue());
    }

    @Test
    public void generateSensorsFunctions() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = heatFlowService.createReport(sample);

        val bound = report.getBound();
        val mean = report.getMeanTemperature();
        val sensors = report.getSensorTemperatures();

        assertChildTemperaturesEqualsMean(sensors, mean);

        sensors.forEach(sensor -> {
            assertFunctionConstantlyGrowing(sensor.getValue());
            assertFunctionNotHigher(sensor.getValue(), bound.getValue());
            assertFunctionNotLower(sensor.getValue(), constantFunction(TIME, 0).getValue());
        });
    }

}