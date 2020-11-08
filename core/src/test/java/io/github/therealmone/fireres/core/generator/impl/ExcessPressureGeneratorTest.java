package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.factory.PointSequenceFactory;
import lombok.val;
import org.junit.Test;

import static io.github.therealmone.fireres.core.TestUtils.defaultGenerationProperties;

public class ExcessPressureGeneratorTest {

    @Test
    public void generateTest() {
        val generatorFactory = new PointSequenceFactory(defaultGenerationProperties());
        val excessPressureGeneratorTest = generatorFactory.excessPressure();
    }
}
