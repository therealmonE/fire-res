package io.github.therealmone.fireres.excess.pressure.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.ExcessPressureGuiceRunner;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionIsConstant;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.excess.pressure.TestGenerationProperties.PRESSURE_DELTA;

@RunWith(ExcessPressureGuiceRunner.class)
public class ExcessPressureReportTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ExcessPressureService excessPressureService;

    @Test
    public void generateMinAllowedPressure() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = excessPressureService.createReport(sample);

        val minAllowedPressure = report.getMinAllowedPressure();

        assertFunctionIsConstant(-PRESSURE_DELTA, minAllowedPressure.getValue());
    }

    @Test
    public void generateMaxAllowedPressure() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = excessPressureService.createReport(sample);

        val maxAllowedPressure = report.getMaxAllowedPressure();

        assertFunctionIsConstant(PRESSURE_DELTA, maxAllowedPressure.getValue());
    }

    @Test
    public void generatePressure() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = excessPressureService.createReport(sample);

        val minAllowedPressure = report.getMinAllowedPressure();
        val maxAllowedPressure = report.getMaxAllowedPressure();

        assertFunctionNotHigher(report.getPressure().getValue(), maxAllowedPressure.getValue());
        assertFunctionNotLower(report.getPressure().getValue(), minAllowedPressure.getValue());
    }

}