package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.exception.InvalidMeanTemperatureException;
import io.github.therealmone.fireres.core.exception.ThermocouplesTemperatureGenerationException;
import io.github.therealmone.fireres.core.generator.MultiplePointSequencesGenerator;
import io.github.therealmone.fireres.core.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.MinAllowedTemperature;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.core.model.ThermocoupleTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.MathUtils.calculateIntsMeanValue;
import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;

@RequiredArgsConstructor
@Slf4j
public class ThermocouplesTempGenerator implements MultiplePointSequencesGenerator<ThermocoupleTemperature> {

    private static final Integer ATTEMPTS = 100;

    private final Integer time;
    private final Integer t0;
    private final ThermocoupleMeanTemperature thermocoupleMeanTemperature;
    private final MinAllowedTemperature minAllowedTemperature;
    private final MaxAllowedTemperature maxAllowedTemperature;
    private final Integer thermocoupleCount;

    @Override
    public List<ThermocoupleTemperature> generate() {
        log.trace("Generating thermocouples temperatures with mean temperature: {}", thermocoupleMeanTemperature.getValue());

        for (int i = 0; i < ATTEMPTS; i++) {
            try {
                return tryToGenerate();
            } catch (ThermocouplesTemperatureGenerationException e) {
                log.error("Failed to generate thermocouples temperatures, retrying...");
            }
        }

        throw new InvalidMeanTemperatureException();
    }

    private ArrayList<ThermocoupleTemperature> tryToGenerate() {
        val thermocouplesTemp = initThermocouplesTemp();

        for (int t = time - 1; t >= 0; t--) {
            val meanTemp = thermocoupleMeanTemperature.getTemperature(t);
            val delta = (int) Math.round(t0 / (double) (t + 1));

            val generatedTemperatures = generateTemperatures(
                    getLowerBounds(t, delta, meanTemp),
                    getUpperBounds(t, thermocouplesTemp, delta, meanTemp),
                    meanTemp
            );

            for (int i = 0; i < thermocoupleCount; i++) {
                thermocouplesTemp.get(i).getValue().set(t, new Point(t, generatedTemperatures.get(i)));
            }
        }

        return thermocouplesTemp;
    }

    private ArrayList<ThermocoupleTemperature> initThermocouplesTemp() {
        return new ArrayList<>() {{
            for (int i = 0; i < thermocoupleCount; i++) {
                val value = IntStream.range(0, time)
                        .mapToObj(t -> new Point(t, 0))
                        .collect(Collectors.toList());

                add(new ThermocoupleTemperature(value));
            }
        }};
    }

    private List<Integer> getLowerBounds(Integer time, Integer delta, Integer meanTemp) {
        val minAllowed = minAllowedTemperature.getSmoothedTemperature(time);

        return IntStream.range(0, thermocoupleCount)
                .mapToObj(i -> Math.max(minAllowed, meanTemp - delta))
                .collect(Collectors.toList());
    }

    private List<Integer> getUpperBounds(Integer iteration,
                                         List<ThermocoupleTemperature> temperatures,
                                         Integer delta, Integer meanTemp) {

        val maxAllowed = maxAllowedTemperature.getSmoothedTemperature(iteration);

        return IntStream.range(0, thermocoupleCount)
                .mapToObj(i -> {
                    if (iteration.equals(time - 1)) {
                        return maxAllowed;
                    } else {
                        val nextTemperature = temperatures.get(i).getTemperature(iteration + 1);
                        return Math.min(Math.min(maxAllowed, meanTemp + delta), nextTemperature);
                    }
                })
                .collect(Collectors.toList());
    }

    private List<Integer> generateTemperatures(List<Integer> lowerBounds, List<Integer> upperBounds, Integer meanTemp) {
        val temperatures = new ArrayList<Integer>() {{
            IntStream.range(0, thermocoupleCount).forEach(i ->
                    add(generateValueInInterval(lowerBounds.get(i), upperBounds.get(i))));
        }};

        if (getDifference(meanTemp, temperatures) != 0) {
            adjustTemperatures(temperatures, meanTemp, lowerBounds, upperBounds);
        }

        return temperatures;
    }

    private void adjustTemperatures(List<Integer> temperatures, Integer meanTemp,
                                    List<Integer> lowerBounds, List<Integer> upperBounds) {

        while (getDifference(meanTemp, temperatures) != 0) {
            val difference = getDifference(meanTemp, temperatures);

            val temperatureIndexToAdjust = resolveTemperatureIndexToAdjust(
                    temperatures, meanTemp, difference, lowerBounds, upperBounds);

            val temperature = temperatures.get(temperatureIndexToAdjust);
            val generatedAdjust = generateAdjust(
                    lowerBounds.get(temperatureIndexToAdjust),
                    upperBounds.get(temperatureIndexToAdjust),
                    difference,
                    temperature);

            temperatures.set(temperatureIndexToAdjust, temperature + generatedAdjust);
        }
    }

    private Integer resolveTemperatureIndexToAdjust(List<Integer> temperatures, Integer meanTemp, Integer difference,
                                                    List<Integer> upperBounds, List<Integer> lowerBounds) {

        return IntStream.range(0, thermocoupleCount)
                .filter(i -> {
                    val temperature = temperatures.get(i);

                    if (difference > 0) {
                        return !temperature.equals(lowerBounds.get(i));
                    } else {
                        return !temperature.equals(upperBounds.get(i));
                    }
                })
                .boxed()
                .max((i1, i2) -> {
                    val delta1 = Math.abs(temperatures.get(i1) - meanTemp);
                    val delta2 = Math.abs(temperatures.get(i2) - meanTemp);

                    return Integer.compare(delta2, delta1);
                })
                .orElseThrow(ThermocouplesTemperatureGenerationException::new);
    }

    private Integer getDifference(Integer meanTemp, List<Integer> temperatures) {
        return meanTemp - calculateIntsMeanValue(temperatures);
    }

    private Integer generateAdjust(Integer lowerBound, Integer upperBound,
                                   Integer differenceToAdjust, Integer temperature) {

        if (differenceToAdjust < 0) {
            return generateAdjustToLowerBound(lowerBound, differenceToAdjust, temperature);
        } else {
            return generateAdjustToUpperBound(upperBound, differenceToAdjust, temperature);
        }
    }

    private Integer generateAdjustToUpperBound(Integer upperBound, Integer differenceToAdjust, Integer temperature) {
        if (temperature.equals(upperBound)) {
            return 0;
        }

        val allowedAdjust = Math.min(upperBound - temperature, differenceToAdjust);

        return generateValueInInterval(1, allowedAdjust);
    }

    private Integer generateAdjustToLowerBound(Integer lowerBound, Integer differenceToAdjust, Integer temperature) {
        if (temperature.equals(lowerBound)) {
            return 0;
        }

        val allowedAdjust = Math.max(lowerBound - temperature, differenceToAdjust);

        return generateValueInInterval(allowedAdjust, -1);

    }


}
