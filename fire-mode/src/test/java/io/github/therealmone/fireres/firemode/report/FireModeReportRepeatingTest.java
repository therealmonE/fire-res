package io.github.therealmone.fireres.firemode.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.FireModeGuiceRunner;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.core.test.TestUtils.assertSizesEquals;
import static io.github.therealmone.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.therealmone.fireres.firemode.TestGenerationProperties.TIME;
import static org.junit.Assert.assertEquals;

@RunWith(FireModeGuiceRunner.class)
public class FireModeReportRepeatingTest {

    private static final Integer ATTEMPTS = 100;

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FireModeService fireModeService;

    @Test
    public void provideReportTest() {
        for (int i = 0; i < ATTEMPTS; i++) {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = fireModeService.createReport(sample);

            val furnaceTemp = report.getFurnaceTemperature().getValue();
            val minAllowedTemp = report.getMinAllowedTemperature().getValue();
            val maxAllowedTemp = report.getMaxAllowedTemperature().getValue();
            val maxAllowedSmoothedTemp = report.getMaxAllowedTemperature().getSmoothedValue();
            val standardTemp = report.getStandardTemperature().getValue();

            //noinspection unchecked
            assertSizesEquals(TIME, furnaceTemp, minAllowedTemp, maxAllowedTemp, maxAllowedSmoothedTemp, standardTemp);

            assertFunctionConstantlyGrowing(minAllowedTemp);
            assertFunctionConstantlyGrowing(maxAllowedSmoothedTemp);
            assertFunctionNotHigher(minAllowedTemp, maxAllowedTemp);
            assertFunctionNotHigher(minAllowedTemp, maxAllowedSmoothedTemp);

            assertFunctionNotLower(standardTemp, minAllowedTemp);
            assertFunctionNotHigher(standardTemp, maxAllowedTemp);
            assertFunctionNotHigher(standardTemp, maxAllowedSmoothedTemp);

            val meanTemp = report.getThermocoupleMeanTemperature();

            assertFunctionConstantlyGrowing(meanTemp.getValue());
            assertFunctionNotLower(meanTemp.getValue(), minAllowedTemp);
            assertFunctionNotHigher(meanTemp.getValue(), maxAllowedTemp);
            assertFunctionNotHigher(meanTemp.getValue(), maxAllowedSmoothedTemp);

            val thermocouplesTemps = report.getThermocoupleTemperatures();

            assertEquals(6, thermocouplesTemps.size());
            assertChildTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

            thermocouplesTemps.forEach(thermocouplesTemp -> {

                assertEquals(TIME, thermocouplesTemp.getValue().size());

                assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
                assertFunctionNotLower(thermocouplesTemp.getValue(), minAllowedTemp);
                assertFunctionNotHigher(thermocouplesTemp.getValue(), maxAllowedTemp);
                assertFunctionNotHigher(thermocouplesTemp.getValue(), maxAllowedSmoothedTemp);

            });
        }
    }

}
