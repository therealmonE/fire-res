package io.github.therealmone.fireres.excess.pressure.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.excess.pressure.GuiceRunner;
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
public class ExcessPressureReportRepeatingTest {

    private static final Integer ATTEMPTS = 100;

    @Inject
    private ExcessPressureReportProvider reportProvider;

    @Test
    public void provideReportTest() {
        for (int i = 0; i < ATTEMPTS; i++) {
            val report = reportProvider.get();

            report.getSamples().forEach(sample -> {
                val min = sample.getMinAllowedPressure();
                val max = sample.getMaxAllowedPressure();
                val pressure = sample.getPressure();

                assertFunctionIsConstant(-PRESSURE_DELTA, min.getValue());
                assertFunctionIsConstant(PRESSURE_DELTA, max.getValue());
                assertSizesEquals(TIME, min.getValue(), max.getValue());
                assertFunctionNotHigher(pressure.getValue(), max.getValue());
                assertFunctionNotLower(pressure.getValue(), min.getValue());
            });
        }
    }

}
