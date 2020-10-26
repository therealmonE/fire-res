package io.github.therealmone.fireres.core.generator.impl;

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

    private final Integer time;
    private final ThermocoupleMeanTemperature thermocoupleMeanTemperature;
    private final MinAllowedTemperature minAllowedTemperature;
    private final MaxAllowedTemperature maxAllowedTemperature;
    private final Integer thermocoupleCount;

    @Override
    public List<ThermocoupleTemperature> generate() {
        log.info("Generating thermocouples temperatures with mean temperature: {}", thermocoupleMeanTemperature.getValue());

        while (true) {
            try {
                return tryToGenerate();
            } catch (ThermocouplesTemperatureGenerationException e) {
                log.error("Failed to generate thermocouples temperatures, retrying...");
            }
        }
    }

    private ArrayList<ThermocoupleTemperature> tryToGenerate() {
        val thermocouplesTemp = initThermocouplesTemp();

        for (int t = time - 1; t >= 0; t--) {
            val meanTemp = thermocoupleMeanTemperature.getValue().get(t).getTemperature();
            val generatedTemperatures = generateTemperatures(
                    getLowerBounds(t),
                    getUpperBounds(t, thermocouplesTemp),
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

    private List<Integer> getLowerBounds(Integer time) {
        val minAllowed = minAllowedTemperature.getSmoothedValue().get(time).getTemperature();

        return IntStream.range(0, thermocoupleCount)
                .mapToObj(i -> minAllowed)
                .collect(Collectors.toList());
    }

    private List<Integer> getUpperBounds(Integer iteration, List<ThermocoupleTemperature> temperatures) {
        val maxAllowed = maxAllowedTemperature.getValue().get(iteration).getTemperature();

        return IntStream.range(0, thermocoupleCount)
                .mapToObj(i -> {
                    if (iteration.equals(time - 1)) {
                        return maxAllowed;
                    } else {
                        val nextTemperature = temperatures.get(i).getValue().get(iteration + 1).getTemperature();
                        return Math.min(maxAllowed, nextTemperature - 1);
                    }
                })
                .collect(Collectors.toList());
    }

    private List<Integer> generateTemperatures(List<Integer> lowerBounds, List<Integer> upperBounds, Integer meanTemp) {
        val temperatures = new ArrayList<Integer>() {{
            IntStream.range(0, thermocoupleCount).forEach(j ->
                    add(generateValueInInterval(lowerBounds.get(j), upperBounds.get(j))));
        }};

        if (getDifference(meanTemp, temperatures) != 0) {
            adjustTemperatures(temperatures, meanTemp, lowerBounds, upperBounds);
        }

        return temperatures;
    }

    private void adjustTemperatures(List<Integer> temperatures, Integer meanTemp,
                                    List<Integer> lowerBounds, List<Integer> upperBounds) {

        while (getDifference(meanTemp, temperatures) != 0) {
            if (temperaturesHitBound(temperatures, lowerBounds) || temperaturesHitBound(temperatures, upperBounds)) {
                throw new ThermocouplesTemperatureGenerationException();
            }

            val difference = getDifference(meanTemp, temperatures);

            for (int i = 0; i < thermocoupleCount; i++) {
                val temperature = temperatures.get(i);
                val generatedAdjust = generateAdjust(
                        lowerBounds.get(i),
                        upperBounds.get(i),
                        difference,
                        temperature);

                temperatures.set(i, temperature + generatedAdjust);

                if (getDifference(meanTemp, temperatures) == 0) {
                    break;
                }
            }
        }
    }

    private boolean temperaturesHitBound(List<Integer> temperatures, List<Integer> bounds) {
        for (int i = 0; i < temperatures.size(); i++) {
            val temperature = temperatures.get(i);
            val bound = bounds.get(i);

            if (!temperature.equals(bound)) {
                return false;
            }
        }

        return true;
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
