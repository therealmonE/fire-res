package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.temperature.TemperatureProperties;
import io.github.therealmone.fireres.core.factory.NumberSequenceGeneratorFactory;
import io.github.therealmone.fireres.core.config.temperature.Coefficient;
import io.github.therealmone.fireres.core.config.temperature.Coefficients;
import lombok.val;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MinAllowedTempGeneratorTest {

    @Test
    public void generate() {
        val factory = new NumberSequenceGeneratorFactory(GenerationProperties.builder()
                .temperature(TemperatureProperties.builder()
                        .environmentTemperature(21)
                        .minAllowedTempCoefficients(new Coefficients(List.of(
                                new Coefficient(0, 10, 0.85),
                                new Coefficient(11, 30, 0.9),
                                new Coefficient(31, 70, 0.95)
                        )))
                        .build())
                .time(71)
                .build());

        val expectedNumbers = List.of(
                18, 280, 361, 410, 445,
                473, 496, 515, 531, 547,
                559, 606, 617, 627, 637,
                647, 655, 663, 671, 679,
                685, 692, 698, 704, 710,
                716, 720, 725, 731, 735,
                740, 786, 789, 794, 798,
                803, 807, 810, 814, 818,
                822, 825, 828, 832, 835,
                838, 842, 845, 847, 850,
                853, 856, 859, 862, 865,
                866, 869, 872, 874, 877,
                879, 882, 884, 886, 888,
                890, 893, 895, 897, 899,
                901
        );

        val standardTemp = factory.standardTempGenerator().generate();
        val minAllowedTemp = factory.minAllowedTempGenerator(standardTemp).generate();

        assertEquals(expectedNumbers, minAllowedTemp.getValue());
    }

}