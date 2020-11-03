package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.factory.PointSequenceGeneratorFactory;
import lombok.val;
import org.junit.Test;

import static io.github.therealmone.fireres.core.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.TestUtils.assertMeanTemperatureInInterval;
import static io.github.therealmone.fireres.core.TestUtils.assertThermocouplesTemperaturesEqualsMean;
import static io.github.therealmone.fireres.core.TestUtils.defaultGenerationProperties;

public class ThermocouplesTempGeneratorTest {

    private static final Integer CYCLES = 1000;

    @Test
    public void generateMultipleTimes() {
        for (int i = 0; i < CYCLES; i++) {
            generate();
        }
    }

    @Test
    public void generate() {
        val factory = new PointSequenceGeneratorFactory(defaultGenerationProperties());

        val standardTemp = factory.standardTempGenerator().generate();
        val minTemp = factory.minAllowedTempGenerator(standardTemp).generate();
        val maxTemp = factory.maxAllowedTempGenerator(standardTemp).generate();

        val meanTemp = factory.thermocoupleMeanGenerator(0, minTemp, maxTemp).generate();

        assertMeanTemperatureInInterval(meanTemp.getValue(), minTemp.getValue(), maxTemp.getValue());
        assertFunctionConstantlyGrowing(meanTemp.getValue());

        val thermocouplesTemp = factory
                .thermocouplesTempGenerator(minTemp, maxTemp, meanTemp, 0)
                .generate();

        assertThermocouplesTemperaturesEqualsMean(thermocouplesTemp, meanTemp);
        thermocouplesTemp.forEach(t -> assertFunctionConstantlyGrowing(t.getValue()));
    }

}