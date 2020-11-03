package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.factory.PointSequenceGeneratorFactory;
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

        val factory = new PointSequenceGeneratorFactory(props);

        val standardTemp = factory.standardTempGenerator().generate();
        val minTemp = factory.minAllowedTempGenerator(standardTemp).generate();
        val maxTemp = factory.maxAllowedTempGenerator(standardTemp).generate();
        val thermocoupleMeanTemp = factory.thermocoupleMeanGenerator(0, minTemp, maxTemp).generate().getValue();

        assertInterpolationPoints(thermocoupleMeanTemp);
        assertFunctionConstantlyGrowing(thermocoupleMeanTemp);
    }

    @Test
    public void generateWithRandomPoints() {
        val factory = new PointSequenceGeneratorFactory(defaultGenerationProperties());

        val standardTemp = factory.standardTempGenerator().generate();
        val minTemp = factory.minAllowedTempGenerator(standardTemp).generate();
        val maxTemp = factory.maxAllowedTempGenerator(standardTemp).generate();
        val thermocoupleMeanTemp = factory.thermocoupleMeanGenerator(0, minTemp, maxTemp).generate().getValue();

        assertInterpolationPoints(thermocoupleMeanTemp);
        assertFunctionConstantlyGrowing(thermocoupleMeanTemp);
    }

    @Test
    public void generateWithRandomPointsAndLowNewPointChance() {
        val props = defaultGenerationProperties();

        props.getSamples().get(0).getRandomPoints().setNewPointChance(0.1);

        val factory = new PointSequenceGeneratorFactory(props);

        val standardTemp = factory.standardTempGenerator().generate();
        val minTemp = factory.minAllowedTempGenerator(standardTemp).generate();
        val maxTemp = factory.maxAllowedTempGenerator(standardTemp).generate();
        val thermocoupleMeanTemp = factory.thermocoupleMeanGenerator(0, minTemp, maxTemp).generate().getValue();

        assertInterpolationPoints(thermocoupleMeanTemp);
        assertFunctionConstantlyGrowing(thermocoupleMeanTemp);
    }

}