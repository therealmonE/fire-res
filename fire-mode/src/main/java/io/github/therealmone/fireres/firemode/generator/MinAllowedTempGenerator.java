package io.github.therealmone.fireres.firemode.generator;

import io.github.therealmone.fireres.firemode.config.Coefficient;
import io.github.therealmone.fireres.firemode.config.Coefficients;
import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.firemode.model.MinAllowedTemperature;
import io.github.therealmone.fireres.firemode.model.StandardTemperature;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.InterpolationUtils.smoothMinFunction;

@RequiredArgsConstructor
@Slf4j
public class MinAllowedTempGenerator implements PointSequenceGenerator<MinAllowedTemperature> {

    private static final Coefficients COEFFICIENTS = new Coefficients(List.of(
            new Coefficient(0, 10, 0.85),
            new Coefficient(11, 30, 0.9),
            new Coefficient(31, Integer.MAX_VALUE, 0.95)
    ));

    private final Integer time;
    private final StandardTemperature standardTemp;

    @Override
    public MinAllowedTemperature generate() {
        val minAllowedTemp = IntStream.range(0, time)
                .mapToObj(t -> new IntegerPoint(t,
                        (int) Math.round(standardTemp.getPoint(t).getValue() * COEFFICIENTS.getCoefficient(t))))
                .collect(Collectors.toList());

        return MinAllowedTemperature.builder()
                .value(minAllowedTemp)
                .smoothedValue(smoothMinFunction(minAllowedTemp))
                .build();
    }

}
