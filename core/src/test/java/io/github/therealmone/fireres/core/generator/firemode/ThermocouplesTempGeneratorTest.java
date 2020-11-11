package io.github.therealmone.fireres.core.generator.firemode;

import io.github.therealmone.fireres.core.firemode.FireModeFactory;
import lombok.val;
import org.junit.Test;

import static io.github.therealmone.fireres.core.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.TestUtils.assertMeanTemperatureInInterval;
import static io.github.therealmone.fireres.core.TestUtils.assertThermocouplesTemperaturesEqualsMean;
import static io.github.therealmone.fireres.core.TestUtils.defaultGenerationProperties;

public class ThermocouplesTempGeneratorTest {

    @Test
    public void generate() {
        val factory = new FireModeFactory(defaultGenerationProperties());

        val standardTemp = factory.standardTemperature();
        val minTemp = factory.minAllowedTemperature(standardTemp);
        val maxTemp = factory.maxAllowedTemperature(standardTemp);

        val meanTemp = factory.thermocoupleMeanTemperature(0, minTemp, maxTemp);

        assertMeanTemperatureInInterval(meanTemp.getValue(), minTemp.getValue(), maxTemp.getValue());
        assertFunctionConstantlyGrowing(meanTemp.getValue());

        val thermocouplesTemp = factory.thermocouplesTemperatures(
                minTemp, maxTemp, meanTemp, 0);

        assertThermocouplesTemperaturesEqualsMean(thermocouplesTemp, meanTemp);
        thermocouplesTemp.forEach(t -> assertFunctionConstantlyGrowing(t.getValue()));
    }

}