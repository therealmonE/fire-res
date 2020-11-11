package io.github.therealmone.fireres.core.common.report;

import lombok.val;
import org.junit.Test;

import static io.github.therealmone.fireres.core.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.core.TestUtils.assertSizesEquals;
import static io.github.therealmone.fireres.core.TestUtils.assertThermocouplesTemperaturesEqualsMean;
import static io.github.therealmone.fireres.core.TestUtils.defaultGenerationProperties;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

public class FullReportBuilderTest {

    private static final Integer CYCLES = 1000;

    @Test
    public void buildMultipleTimes() {
        for (int i = 0; i < CYCLES; i++) {
            build();
        }
    }

    @Test
    public void build() {
        val report = new FullReportBuilder().build(defaultGenerationProperties());

        val time = (int) report.getTime();
        val environmentTemp = (int) report.getEnvironmentTemperature();
        val furnaceTemp = report.getFireMode().getFurnaceTemperature().getValue();
        val minAllowedTemp = report.getFireMode().getMinAllowedTemperature().getValue();
        val maxAllowedTemp = report.getFireMode().getMaxAllowedTemperature().getValue();
        val maxAllowedSmoothedTemp = report.getFireMode().getMaxAllowedTemperature().getSmoothedValue();
        val standardTemp = report.getFireMode().getStandardTemperature().getValue();

        assertEquals(71, time);
        assertEquals(21, environmentTemp);

        //noinspection unchecked
        assertSizesEquals(time, furnaceTemp, minAllowedTemp, maxAllowedTemp, maxAllowedSmoothedTemp, standardTemp);

        assertFunctionConstantlyGrowing(minAllowedTemp);
        assertFunctionConstantlyGrowing(maxAllowedSmoothedTemp);
        assertFunctionNotHigher(minAllowedTemp, maxAllowedTemp);
        assertFunctionNotHigher(minAllowedTemp, maxAllowedSmoothedTemp);

        assertFunctionNotLower(standardTemp, minAllowedTemp);
        assertFunctionNotHigher(standardTemp, maxAllowedTemp);
        assertFunctionNotHigher(standardTemp, maxAllowedSmoothedTemp);

        report.getFireMode().getSamples().forEach(sample -> {
            val meanTemp = sample.getThermocoupleMeanTemperature();

            assertFunctionConstantlyGrowing(meanTemp.getValue());
            assertFunctionNotLower(meanTemp.getValue(), minAllowedTemp);
            assertFunctionNotHigher(meanTemp.getValue(), maxAllowedTemp);
            assertFunctionNotHigher(meanTemp.getValue(), maxAllowedSmoothedTemp);

            val thermocouplesTemps = sample.getThermocoupleTemperatures();

            assertEquals(6, thermocouplesTemps.size());
            assertThermocouplesTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

            thermocouplesTemps.forEach(thermocouplesTemp -> {

                assertEquals(time, thermocouplesTemp.getValue().size());

                assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
                assertFunctionNotLower(thermocouplesTemp.getValue(), minAllowedTemp);
                assertFunctionNotHigher(thermocouplesTemp.getValue(), maxAllowedTemp);
                assertFunctionNotHigher(thermocouplesTemp.getValue(), maxAllowedSmoothedTemp);

            });
        });
    }

    @Test
    public void buildWithoutInterpolationPoints() {
        val props = defaultGenerationProperties();

        props.getSamples().get(0).getFireMode().setInterpolationPoints(emptyList());

        val report = new FullReportBuilder().build(props);

        val time = (int) report.getTime();
        val environmentTemp = (int) report.getEnvironmentTemperature();
        val furnaceTemp = report.getFireMode().getFurnaceTemperature().getValue();
        val minAllowedTemp = report.getFireMode().getMinAllowedTemperature().getValue();
        val maxAllowedTemp = report.getFireMode().getMaxAllowedTemperature().getValue();
        val maxAllowedSmoothedTemp = report.getFireMode().getMaxAllowedTemperature().getSmoothedValue();
        val standardTemp = report.getFireMode().getStandardTemperature().getValue();

        assertEquals(71, time);
        assertEquals(21, environmentTemp);

        //noinspection unchecked
        assertSizesEquals(time, furnaceTemp, minAllowedTemp, maxAllowedTemp, maxAllowedSmoothedTemp, standardTemp);

        assertFunctionConstantlyGrowing(minAllowedTemp);
        assertFunctionConstantlyGrowing(maxAllowedSmoothedTemp);
        assertFunctionNotHigher(minAllowedTemp, maxAllowedTemp);
        assertFunctionNotHigher(minAllowedTemp, maxAllowedSmoothedTemp);

        assertFunctionNotLower(standardTemp, minAllowedTemp);
        assertFunctionNotHigher(standardTemp, maxAllowedTemp);
        assertFunctionNotHigher(standardTemp, maxAllowedSmoothedTemp);

        report.getFireMode().getSamples().forEach(sample -> {
            val meanTemp = sample.getThermocoupleMeanTemperature();

            assertFunctionConstantlyGrowing(meanTemp.getValue());
            assertFunctionNotLower(meanTemp.getValue(), minAllowedTemp);
            assertFunctionNotHigher(meanTemp.getValue(), maxAllowedTemp);
            assertFunctionNotHigher(meanTemp.getValue(), maxAllowedSmoothedTemp);

            val thermocouplesTemps = sample.getThermocoupleTemperatures();

            assertEquals(6, thermocouplesTemps.size());
            assertThermocouplesTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

            thermocouplesTemps.forEach(thermocouplesTemp -> {

                assertEquals(time, thermocouplesTemp.getValue().size());

                assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
                assertFunctionNotLower(thermocouplesTemp.getValue(), minAllowedTemp);
                assertFunctionNotHigher(thermocouplesTemp.getValue(), maxAllowedTemp);
                assertFunctionNotHigher(thermocouplesTemp.getValue(), maxAllowedSmoothedTemp);

            });
        });
    }

}