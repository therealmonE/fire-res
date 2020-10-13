package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.InterpolationPoints;
import io.github.therealmone.fireres.core.config.Point;
import io.github.therealmone.fireres.core.factory.NumberSequenceGeneratorFactory;
import lombok.val;
import org.junit.Test;

import java.util.List;

public class ThermocoupleMeanGeneratorTest {

    @Test
    public void generate() {
        val factory = new NumberSequenceGeneratorFactory(GenerationProperties.builder()
                .time(70)
                .interpolationPoints(new InterpolationPoints(List.of(
                        new Point(0, 21),
                        new Point(1, 306),
                        new Point(18, 707),
                        new Point(21, 772),
                        new Point(26, 812),
                        new Point(48, 861),
                        new Point(49, 892),
                        new Point(70, 943)
                )))
                .build());

        val thermocoupleMeanTemp = factory.thermocoupleMeanGenerator().generate();
        System.out.println(thermocoupleMeanTemp.getValue());
    }

}