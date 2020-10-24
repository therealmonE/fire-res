package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.temperature.TemperatureProperties;
import io.github.therealmone.fireres.core.factory.PointSequenceGeneratorFactory;
import io.github.therealmone.fireres.core.config.temperature.Coefficient;
import lombok.val;
import org.junit.Test;

import java.util.List;

import static io.github.therealmone.fireres.core.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.TestUtils.toPointList;
import static org.junit.Assert.assertEquals;

public class MaxAllowedTempGeneratorTest {

    @Test
    public void generate() {
        val factory = new PointSequenceGeneratorFactory(GenerationProperties.builder()
                .temperature(TemperatureProperties.builder()
                        .environmentTemperature(21)
                        .maxAllowedTempCoefficients(List.of(
                                new Coefficient(0, 10, 1.15),
                                new Coefficient(11, 30, 1.1),
                                new Coefficient(31, 70, 1.05)
                        ))
                        .build())
                .time(71)
                .build());

        val expectedFunction = toPointList(List.of(
                24, 378, 489, 554, 603,
                639, 670, 697, 719, 739,
                757, 740, 754, 767, 779,
                791, 801, 811, 821, 829,
                837, 846, 854, 860, 868,
                875, 880, 887, 893, 899,
                904, 868, 873, 878, 882,
                887, 891, 896, 900, 904,
                908, 911, 916, 920, 923,
                926, 930, 933, 937, 940,
                943, 946, 949, 952, 956,
                958, 961, 964, 966, 969,
                971, 974, 977, 980, 982,
                984, 987, 989, 991, 993,
                995
        ));

        val standardTemp = factory.standardTempGenerator().generate();
        val maxAllowedTemp = factory.maxAllowedTempGenerator(standardTemp).generate();

        assertEquals(expectedFunction, maxAllowedTemp.getValue());

        val smoothedFunction = maxAllowedTemp.getSmoothedValue();

        assertFunctionConstantlyGrowing(smoothedFunction);
        assertEquals(expectedFunction.size(), smoothedFunction.size());
        assertFunctionNotHigher(smoothedFunction, expectedFunction);
    }

}