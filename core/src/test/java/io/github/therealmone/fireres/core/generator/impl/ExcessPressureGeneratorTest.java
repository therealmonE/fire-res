package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.factory.PointSequenceFactory;
import io.github.therealmone.fireres.core.model.point.DoublePoint;
import lombok.val;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.TestUtils.*;
import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;
import static org.junit.Assert.assertTrue;

public class ExcessPressureGeneratorTest {

    @Test
    public void generateTest() {
        val generatorFactory = new PointSequenceFactory(defaultGenerationProperties());
        val excessPressureGeneratorTest = generatorFactory.excessPressure();
        System.out.println(excessPressureGeneratorTest);

//        assertFunctionNotHigherDouble(IntStream.range(0, time)
//                .mapToObj(PointSequenceFactory.generationProperties.getTime())
//                .collect(Collectors.toList());
//        assertFunctionNotLowerDouble();
    }
}
