package io.github.therealmone.fireres.core.report;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.interpolation.InterpolationMethod;
import io.github.therealmone.fireres.core.config.interpolation.InterpolationProperties;
import io.github.therealmone.fireres.core.config.random.RandomPointsProperties;
import io.github.therealmone.fireres.core.config.sample.SampleProperties;
import io.github.therealmone.fireres.core.config.temperature.Coefficient;
import io.github.therealmone.fireres.core.config.temperature.TemperatureProperties;
import io.github.therealmone.fireres.core.model.Point;
import lombok.val;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.github.therealmone.fireres.core.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.core.TestUtils.assertSizesEquals;
import static io.github.therealmone.fireres.core.TestUtils.assertThermocouplesTemperaturesEqualsMean;
import static org.junit.Assert.assertEquals;

public class ReportBuilderTest {

    private static final List<Point> INTERPOLATION_POINTS = new ArrayList<>() {{
        add(new Point(0, 21));
        add(new Point(1, 306));
        add(new Point(2, 392));
        add(new Point(3, 480));
        add(new Point(8, 615));
        add(new Point(18, 749));
        add(new Point(21, 789));
        add(new Point(26, 822));
        add(new Point(48, 898));
        add(new Point(49, 901));
        add(new Point(70, 943));
    }};

    @Test
    public void build() {
        val properties = GenerationProperties.builder()
                .time(71)
                .temperature(TemperatureProperties.builder()
                        .environmentTemperature(21)
                        .minAllowedTempCoefficients(List.of(
                                new Coefficient(0, 10, 0.85),
                                new Coefficient(11, 30, 0.9),
                                new Coefficient(31, 71, 0.95)
                        ))
                        .maxAllowedTempCoefficients(List.of(
                                new Coefficient(0, 10, 1.15),
                                new Coefficient(11, 30, 1.1),
                                new Coefficient(31, 71, 1.05)
                        ))
                        .build())
                .samples(List.of(SampleProperties.builder()
                        .randomPoints(RandomPointsProperties.builder()
                                .enrichWithRandomPoints(true)
                                .newPointChance(1.0)
                                .build())
                        .interpolation(InterpolationProperties.builder()
                                .interpolationPoints(INTERPOLATION_POINTS)
                                .interpolationMethod(InterpolationMethod.LINEAR)
                                .build())
                        .thermocoupleCount(6)
                        .build()))
                .build();

        val report = ReportBuilder.build(properties);

        val time = (int) report.getTime();
        val environmentTemp = (int) report.getEnvironmentTemperature();
        val furnaceTemp = report.getFurnaceTemperature().getValue();
        val minAllowedTemp = report.getMinAllowedTemperature().getValue();
        val maxAllowedTemp = report.getMaxAllowedTemperature().getValue();
        val maxAllowedSmoothedTemp = report.getMaxAllowedTemperature().getSmoothedValue();
        val standardTemp = report.getStandardTemperature().getValue();

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

        report.getSamples().forEach(sample -> {
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