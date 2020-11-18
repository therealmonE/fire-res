package io.github.therealmone.fireres.excess.pressure.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.excess.pressure.GuiceRunner;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.excess.pressure.TestGenerationProperties.PRESSURE_DELTA;
import static io.github.therealmone.fireres.excess.pressure.TestGenerationProperties.TIME;
import static io.github.therealmone.fireres.excess.pressure.TestUtils.assertFunctionIsConstant;
import static io.github.therealmone.fireres.excess.pressure.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.excess.pressure.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.excess.pressure.TestUtils.assertSizesEquals;

@RunWith(GuiceRunner.class)
public class ExcessPressureReportTest {

    @Inject
    private ExcessPressureReportProvider reportProvider;

    @Test
    public void generateMinAllowedPressure() {
        val minAllowedPressure = reportProvider.get().getMinAllowedPressure();

        assertFunctionIsConstant(-PRESSURE_DELTA, minAllowedPressure.getValue());
    }

    @Test
    public void generateMaxAllowedPressure() {
        val maxAllowedPressure = reportProvider.get().getMaxAllowedPressure();

        assertFunctionIsConstant(PRESSURE_DELTA, maxAllowedPressure.getValue());
    }

    @Test
    public void generatePressure() {
        val report = reportProvider.get();

        val minAllowedPressure = report.getMinAllowedPressure();
        val maxAllowedPressure = report.getMaxAllowedPressure();
        val samples = report.getSamples();

        for (ExcessPressureSample sample : samples) {
            assertFunctionNotHigher(sample.getPressure().getValue(), maxAllowedPressure.getValue());
            assertFunctionNotLower(sample.getPressure().getValue(), minAllowedPressure.getValue());
        }
    }

    @Test
    public void provideReportTest() {
        val report = reportProvider.get();

        val min = report.getMinAllowedPressure();
        val max = report.getMaxAllowedPressure();

        assertFunctionIsConstant(-PRESSURE_DELTA, min.getValue());
        assertFunctionIsConstant(PRESSURE_DELTA, max.getValue());

        assertSizesEquals(TIME, min.getValue(), max.getValue());

        report.getSamples().forEach(sample -> {
            val pressure = sample.getPressure();

            assertFunctionNotHigher(pressure.getValue(), max.getValue());
            assertFunctionNotLower(pressure.getValue(), min.getValue());
        });
    }

}