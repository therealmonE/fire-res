package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.generator.MultipleNumberSequencesGenerator;
import io.github.therealmone.fireres.core.model.FurnaceTemperature;
import io.github.therealmone.fireres.core.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.MinAllowedTemperature;
import io.github.therealmone.fireres.core.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.core.model.ThermocoupleTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.MathUtils.calculateMeanValue;
import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;

@RequiredArgsConstructor
@Slf4j
public class ThermocouplesTempGenerator implements MultipleNumberSequencesGenerator<ThermocoupleTemperature> {

    private final Integer time;
    private final ThermocoupleMeanTemperature thermocoupleMeanTemperature;
    private final MinAllowedTemperature minAllowedTemperature;
    private final MaxAllowedTemperature maxAllowedTemperature;
    private final Integer thermocoupleCount;

    @Override
    public List<ThermocoupleTemperature> generate() {
        log.info("Generating thermocouples temperatures with mean temperature: {}", thermocoupleMeanTemperature);

        val thermocouplesTemp = new ArrayList<ThermocoupleTemperature>() {{
            for (int i = 0; i < thermocoupleCount; i++) {
                add(new ThermocoupleTemperature(new ArrayList<>()));
            }
        }};

        for (int i = 0; i < time; i++) {
            val meanTemp = thermocoupleMeanTemperature.getValue().get(i);
            val generatedTemperatures = generateTemperatures(
                    getLowerBounds(i, thermocouplesTemp),
                    getUpperBounds(i),
                    meanTemp
            );

            for (int j = 0; j < thermocoupleCount; j++) {
                thermocouplesTemp.get(j).getValue().add(i, generatedTemperatures.get(j));
            }
        }

        return thermocouplesTemp;
    }

    private List<Integer> getLowerBounds(Integer time, List<ThermocoupleTemperature> temperatures) {
        val minAllowed = minAllowedTemperature.getValue().get(time);

        return IntStream.range(0, thermocoupleCount)
                .mapToObj(i -> {
                    if (temperatures.get(i).getValue().isEmpty()) {
                        return minAllowed;
                    } else {
                        return temperatures.get(i).getValue().get(time - 1) + 1;
                    }
                })
                .collect(Collectors.toList());
    }

    private List<Integer> getUpperBounds(Integer iteration) {
        val maxAllowed = maxAllowedTemperature.getValue().get(iteration);

        return IntStream.range(0, thermocoupleCount)
                .mapToObj(i -> maxAllowed)
                .collect(Collectors.toList());
    }

    private List<Integer> generateTemperatures(List<Integer> lowerBounds, List<Integer> upperBounds, Integer meanTemp) {
        val temperatures = new ArrayList<Integer>() {{
            IntStream.range(0, thermocoupleCount).forEach(j ->
                    add(generateValueInInterval(lowerBounds.get(j), upperBounds.get(j))));
        }};

        val difference = meanTemp - calculateMeanValue(temperatures);

        if (difference != 0) {
            adjustTemperatures(temperatures, difference, lowerBounds, upperBounds);
        }

        return temperatures;
    }

    private void adjustTemperatures(List<Integer> temperatures, Integer difference,
                                    List<Integer> lowerBounds, List<Integer> upperBounds) {

        var remainingDifference = difference * thermocoupleCount;

        while (remainingDifference != 0) {
            log.info("Adjusting temperatures: {}, remainingDifference: {}", temperatures, remainingDifference);
            for (int i = 0; i < thermocoupleCount; i++) {
                val temperature = temperatures.get(i);
                val generatedAdjust = generateAdjust(
                        lowerBounds.get(i),
                        upperBounds.get(i),
                        remainingDifference,
                        temperature);

                temperatures.set(i, temperature + generatedAdjust);
                remainingDifference -= generatedAdjust;

                if (remainingDifference == 0) {
                    break;
                }
            }
        }
    }

    private Integer generateAdjust(Integer lowerBound, Integer upperBound,
                                   Integer remainingDifference, Integer temperature) {

        if (remainingDifference < 0) {
            return generateAdjustToLowerBound(lowerBound, remainingDifference, temperature);
        } else {
            return generateAdjustToUpperBound(upperBound, remainingDifference, temperature);
        }
    }

    private Integer generateAdjustToUpperBound(Integer upperBound, Integer remainingDifference, Integer temperature) {
        if (temperature.equals(upperBound)) {
            return 0;
        }

        val differenceToAdjust = remainingDifference / thermocoupleCount;
        val allowedAdjust = Math.min(upperBound - temperature, differenceToAdjust != 0 ? differenceToAdjust : 1);

        return generateValueInInterval(1, allowedAdjust);
    }

    private Integer generateAdjustToLowerBound(Integer lowerBound, Integer remainingDifference, Integer temperature) {
        if (temperature.equals(lowerBound)) {
            return 0;
        }

        val differenceToAdjust = remainingDifference / thermocoupleCount;
        val allowedAdjust = Math.max(lowerBound - temperature, differenceToAdjust != 0 ? differenceToAdjust : -1);

        return generateValueInInterval(allowedAdjust, -1);

    }


}
