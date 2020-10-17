package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.config.InterpolationMethod;
import io.github.therealmone.fireres.core.config.InterpolationPoints;
import io.github.therealmone.fireres.core.model.ThermocoupleMeanTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.InterpolationUtils.addZeroPointIfNeeded;
import static io.github.therealmone.fireres.core.utils.InterpolationUtils.enrichWithRandomPoints;
import static io.github.therealmone.fireres.core.utils.InterpolationUtils.getTemperatureArray;
import static io.github.therealmone.fireres.core.utils.InterpolationUtils.getTimeArray;

@RequiredArgsConstructor
@Slf4j
public class ThermocoupleMeanGenerator implements NumberSequenceGenerator<ThermocoupleMeanTemperature> {

    private final Integer t0;
    private final Integer time;
    private final InterpolationPoints interpolationPoints;
    private final InterpolationMethod interpolationMethod;
    private final Boolean enrichWithRandomPoints;
    private final Double newPointChance;

    @Override
    public ThermocoupleMeanTemperature generate() {
        log.info("Generating thermocouple mean temperature with interpolation points: {}",
                interpolationPoints.getPoints());

        val points = new ArrayList<>(interpolationPoints.getPoints());
        addZeroPointIfNeeded(points, t0);

        if (enrichWithRandomPoints) {
            enrichWithRandomPoints(points, time, newPointChance);
        }

        val function = interpolationMethod.getInterpolator().interpolate(
                getTimeArray(points),
                getTemperatureArray(points));

        val thermocoupleMeanTemp = IntStream.range(0, time)
                .map(i -> (int) Math.round(function.value(i)))
                .boxed()
                .collect(Collectors.toList());

        log.info("Generated thermocouple mean temperature: {}", thermocoupleMeanTemp);
        return new ThermocoupleMeanTemperature(thermocoupleMeanTemp);
    }







}
