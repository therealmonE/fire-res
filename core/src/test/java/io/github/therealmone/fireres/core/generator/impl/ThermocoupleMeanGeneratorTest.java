package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.factory.PointSequenceFactory;
import lombok.val;
import org.junit.Test;

import static io.github.therealmone.fireres.core.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.TestUtils.assertInterpolationPoints;
import static io.github.therealmone.fireres.core.TestUtils.defaultGenerationProperties;

public class ThermocoupleMeanGeneratorTest {

    @Test
    public void generateWithoutRandomPoints() {
        val props = defaultGenerationProperties();

        props.getSamples().get(0).getRandomPoints().setEnrichWithRandomPoints(false);

        val factory = new PointSequenceFactory(props);

        val standardTemp = factory.standardTemperature();
        val minTemp = factory.minAllowedTemperature(standardTemp);
        val maxTemp = factory.maxAllowedTemperature(standardTemp);
        val thermocoupleMeanTemp = factory.thermocoupleMeanTemperature(0, minTemp, maxTemp).getValue();

        assertInterpolationPoints(thermocoupleMeanTemp);
        assertFunctionConstantlyGrowing(thermocoupleMeanTemp);
    }

    @Test
    public void generateWithRandomPoints() {
        val factory = new PointSequenceFactory(defaultGenerationProperties());

        val standardTemp = factory.standardTemperature();
        val minTemp = factory.minAllowedTemperature(standardTemp);
        val maxTemp = factory.maxAllowedTemperature(standardTemp);
        val thermocoupleMeanTemp = factory.thermocoupleMeanTemperature(0, minTemp, maxTemp).getValue();

        assertInterpolationPoints(thermocoupleMeanTemp);
        assertFunctionConstantlyGrowing(thermocoupleMeanTemp);
    }

    @Test
    public void generateWithRandomPointsAndLowNewPointChance() {
        val props = defaultGenerationProperties();

        props.getSamples().get(0).getRandomPoints().setNewPointChance(0.1);

        val factory = new PointSequenceFactory(props);

        val standardTemp = factory.standardTemperature();
        val minTemp = factory.minAllowedTemperature(standardTemp);
        val maxTemp = factory.maxAllowedTemperature(standardTemp);
        val thermocoupleMeanTemp = factory.thermocoupleMeanTemperature(0, minTemp, maxTemp).getValue();

        assertInterpolationPoints(thermocoupleMeanTemp);
        assertFunctionConstantlyGrowing(thermocoupleMeanTemp);
    }

}