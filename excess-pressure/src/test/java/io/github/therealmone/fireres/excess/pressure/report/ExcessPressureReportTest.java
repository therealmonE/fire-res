package io.github.therealmone.fireres.excess.pressure.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.excess.pressure.GuiceRunner;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.excess.pressure.TestGenerationProperties.PRESSURE_DELTA;
import static io.github.therealmone.fireres.excess.pressure.TestUtils.assertFunctionIsConstant;
import static io.github.therealmone.fireres.excess.pressure.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.excess.pressure.TestUtils.assertFunctionNotLower;

@RunWith(GuiceRunner.class)
public class ExcessPressureReportTest {

    @Inject
    private ExcessPressureReportProvider reportProvider;

    @Test
    public void generateMinAllowedPressure() {
        reportProvider.get().getSamples().forEach(sample -> {
            val minAllowedPressure = sample.getMinAllowedPressure();

            assertFunctionIsConstant(-PRESSURE_DELTA, minAllowedPressure.getValue());
        });
    }

    @Test
    public void generateMaxAllowedPressure() {
        reportProvider.get().getSamples().forEach(sample -> {
            val maxAllowedPressure = sample.getMaxAllowedPressure();

            assertFunctionIsConstant(PRESSURE_DELTA, maxAllowedPressure.getValue());
        });
    }

    @Test
    public void generatePressure() {
        reportProvider.get().getSamples().forEach(sample -> {
            val minAllowedPressure = sample.getMinAllowedPressure();
            val maxAllowedPressure = sample.getMaxAllowedPressure();

            assertFunctionNotHigher(sample.getPressure().getValue(), maxAllowedPressure.getValue());
            assertFunctionNotLower(sample.getPressure().getValue(), minAllowedPressure.getValue());
        });
    }

}