package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.config.InterpolationPoints;
import io.github.therealmone.fireres.core.model.ThermocoupleMeanTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
public class ThermocoupleMeanGenerator implements NumberSequenceGenerator<ThermocoupleMeanTemperature> {

    private final Integer time;
    private final InterpolationPoints interpolationPoints;

    @Override
    public ThermocoupleMeanTemperature generate() {
        log.info("Generating thermocouple mean temperature with interpolation points: {}", interpolationPoints);

        val function = new LinearInterpolator().interpolate(
                interpolationPoints.getX(),
                interpolationPoints.getY());

        val thermocoupleMeanTemp = IntStream.range(0, time)
                .map(i -> (int) Math.round(function.value(i)))
                .boxed()
                .collect(Collectors.toList());

        log.info("Generated thermocouple mean temperature: {}", thermocoupleMeanTemp);
        return new ThermocoupleMeanTemperature(thermocoupleMeanTemp);
    }

}
