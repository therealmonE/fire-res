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
import static io.github.therealmone.fireres.core.test.TestUtils.assertSizesEquals;
import static io.github.therealmone.fireres.core.test.TestUtils.repeatTest;
import static io.github.therealmone.fireres.excess.pressure.TestGenerationProperties.PRESSURE_DELTA;
import static io.github.therealmone.fireres.excess.pressure.TestGenerationProperties.TIME;

@RunWith(ExcessPressureGuiceRunner.class)
public class ExcessPressureReportRepeatingTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ExcessPressureService excessPressureService;

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = excessPressureService.createReport(sample);

            val min = report.getMinAllowedPressure();
            val max = report.getMaxAllowedPressure();
            val pressure = report.getPressure();

            assertFunctionIsConstant(-PRESSURE_DELTA, min.getValue());
            assertFunctionIsConstant(PRESSURE_DELTA, max.getValue());
            assertSizesEquals(TIME, min.getValue(), max.getValue());
            assertFunctionNotHigher(pressure.getValue(), max.getValue());
            assertFunctionNotLower(pressure.getValue(), min.getValue());
        });
    }

}
