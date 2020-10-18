package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.generator.MultipleNumberSequencesGenerator;
import io.github.therealmone.fireres.core.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.MinAllowedTemperature;
import io.github.therealmone.fireres.core.model.ThermocoupleMeanTemperature;
import io.github.therealmone.fireres.core.model.ThermocoupleTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
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
            val generatedTemperatures = generateTemperatures(i, meanTemp);

            for (int j = 0; j < thermocoupleCount; j++) {
                thermocouplesTemp.get(j).getValue().add(i, generatedTemperatures.get(j));
            }
        }

        return thermocouplesTemp;
    }

    private List<Integer> generateTemperatures(Integer i, Integer meanTemp) {
        val lowerBound = minAllowedTemperature.getValue().get(i);
        val upperBound = maxAllowedTemperature.getValue().get(i);

        val temperatures = new ArrayList<Integer>() {{
            IntStream.range(0, thermocoupleCount).forEach(j -> add(generateValueInInterval(lowerBound, upperBound)));
        }};

        val difference = meanTemp - calculateMeanValue(temperatures);
        adjustTemperatures(temperatures, difference, lowerBound, upperBound);

        return temperatures;
    }

    private void adjustTemperatures(List<Integer> temperatures, Integer difference,
                                    Integer lowerBound, Integer upperBound) {
        //todo
    }


}
