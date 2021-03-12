package io.github.therealmone.fireres.firemode.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.FireModeGuiceRunner;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.core.test.TestUtils.assertSizesEquals;
import static io.github.therealmone.fireres.core.test.TestUtils.repeatTest;
import static io.github.therealmone.fireres.firemode.TestGenerationProperties.TIME;
import static org.junit.Assert.assertEquals;

@RunWith(FireModeGuiceRunner.class)
public class FireModeReportWithShiftedBoundsRepeatingTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FireModeService fireModeService;

    @Before
    public void setUpBoundsShift() {
        val sampleProperties = generationProperties.getSamples().get(0);
        val reportProperties = sampleProperties
                .getReportPropertiesByClass(FireModeProperties.class)
                .orElseThrow();

        reportProperties.getFunctionForm().getInterpolationPoints().clear();

        val boundsShift = reportProperties.getBoundsShift();

        boundsShift.getMaxAllowedTemperatureShift().add(new IntegerPoint(10, -5));
        boundsShift.getMaxAllowedTemperatureShift().add(new IntegerPoint(50, 100));

        boundsShift.getMinAllowedTemperatureShift().add(new IntegerPoint(10, 5));
        boundsShift.getMinAllowedTemperatureShift().add(new IntegerPoint(50, -100));
    }

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = fireModeService.createReport(sample);
            val boundsShift = report.getProperties().getBoundsShift();

            val furnaceTemp = report.getFurnaceTemperature().getValue();

            val minAllowedTemp =
                    report.getMinAllowedTemperature()
                            .getShiftedValue(boundsShift.getMinAllowedTemperatureShift());

            val maxAllowedTemp =
                    report.getMaxAllowedTemperature()
                            .getShiftedValue(boundsShift.getMaxAllowedTemperatureShift());

            val standardTemp = report.getStandardTemperature().getValue();

            //noinspection unchecked
            assertSizesEquals(TIME, furnaceTemp, minAllowedTemp, maxAllowedTemp, standardTemp);

            assertFunctionNotHigher(minAllowedTemp, maxAllowedTemp);

            assertFunctionNotLower(standardTemp, minAllowedTemp);
            assertFunctionNotHigher(standardTemp, maxAllowedTemp);

            val meanTemp = report.getThermocoupleMeanTemperature();

            assertFunctionConstantlyGrowing(meanTemp.getValue());
            assertFunctionNotLower(meanTemp.getValue(), minAllowedTemp);
            assertFunctionNotHigher(meanTemp.getValue(), maxAllowedTemp);

            val thermocouplesTemps = report.getThermocoupleTemperatures();

            assertEquals(6, thermocouplesTemps.size());
            assertChildTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

            thermocouplesTemps.forEach(thermocouplesTemp -> {

                assertEquals(TIME, thermocouplesTemp.getValue().size());

                assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
                assertFunctionNotLower(thermocouplesTemp.getValue(), minAllowedTemp);
                assertFunctionNotHigher(thermocouplesTemp.getValue(), maxAllowedTemp);

            });
        });
    }

}
