package io.github.therealmone.fireres.heatflow.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.heatflow.HeatFlowGuiceRunner;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPoint;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.heatflow.TestGenerationProperties.TIME;

@RunWith(HeatFlowGuiceRunner.class)
public class HeatFlowReportWithShiftedBoundsRepeatingTest {

    private static final Integer CYCLES = 100;

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private HeatFlowService heatFlowService;

    @Before
    public void setUpBoundsShift() {
        val sampleProperties = generationProperties.getSamples().get(0);
        val reportProperties = sampleProperties
                .getReportPropertiesByClass(HeatFlowProperties.class)
                .orElseThrow();

        reportProperties.getFunctionForm().getInterpolationPoints().clear();

        val boundsShift = reportProperties.getBoundsShift();

        boundsShift.getMaxAllowedFlowShift().add(new HeatFlowPoint(10, -0.5));
        boundsShift.getMaxAllowedFlowShift().add(new HeatFlowPoint(50, 1.0));
    }

    @Test
    public void provideReportTest() {
        for (int i = 0; i < CYCLES; i++) {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = heatFlowService.createReport(sample);

            val bound = report.getBound()
                    .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedFlowShift());

            val mean = report.getMeanTemperature();

            assertFunctionConstantlyGrowing(mean.getValue());
            assertFunctionNotLower(mean.getValue(), constantFunction(TIME, 0).getValue());
            assertFunctionNotHigher(mean.getValue(), bound);

            val sensors = report.getSensorTemperatures();

            assertChildTemperaturesEqualsMean(sensors, mean);

            sensors.forEach(sensor -> {
                assertFunctionConstantlyGrowing(sensor.getValue());
                assertFunctionNotHigher(sensor.getValue(), bound);
                assertFunctionNotLower(sensor.getValue(), constantFunction(TIME, 0).getValue());
            });
        }
    }

}
