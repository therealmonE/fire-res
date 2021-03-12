package io.github.therealmone.fireres.excess.pressure.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.DoublePoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.ExcessPressureGuiceRunner;
import io.github.therealmone.fireres.excess.pressure.config.ExcessPressureProperties;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.core.test.TestUtils.assertSizesEquals;
import static io.github.therealmone.fireres.core.test.TestUtils.repeatTest;
import static io.github.therealmone.fireres.excess.pressure.TestGenerationProperties.TIME;

@RunWith(ExcessPressureGuiceRunner.class)
public class ExcessPressureReportWithShiftedBoundsRepeatingTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ExcessPressureService excessPressureService;

    @Before
    public void setUpBoundsShift() {
        val sampleProperties = generationProperties.getSamples().get(0);
        val reportProperties = sampleProperties
                .getReportPropertiesByClass(ExcessPressureProperties.class)
                .orElseThrow();

        val boundsShift = reportProperties.getBoundsShift();

        boundsShift.getMaxAllowedPressureShift().add(new DoublePoint(10, -0.5));
        boundsShift.getMaxAllowedPressureShift().add(new DoublePoint(30, 1.5));

        boundsShift.getMinAllowedPressureShift().add(new DoublePoint(10, 0.5));
        boundsShift.getMinAllowedPressureShift().add(new DoublePoint(30, -1.5));
    }

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = excessPressureService.createReport(sample);

            val min = report.getMinAllowedPressure()
                    .getShiftedValue(report.getProperties().getBoundsShift().getMinAllowedPressureShift());

            val max = report.getMaxAllowedPressure()
                    .getShiftedValue(report.getProperties().getBoundsShift().getMaxAllowedPressureShift());

            val pressure = report.getPressure();

            assertSizesEquals(TIME, min, max);
            assertFunctionNotHigher(pressure.getValue(), max);
            assertFunctionNotLower(pressure.getValue(), min);
        });
    }

}
