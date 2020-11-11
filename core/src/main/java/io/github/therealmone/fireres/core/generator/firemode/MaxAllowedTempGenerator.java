package io.github.therealmone.fireres.core.generator.firemode;

import io.github.therealmone.fireres.core.config.firemode.Coefficient;
import io.github.therealmone.fireres.core.config.firemode.Coefficients;
import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.firemode.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.firemode.StandardTemperature;
import io.github.therealmone.fireres.core.model.point.IntegerPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.InterpolationUtils.smoothMaxFunction;

@RequiredArgsConstructor
@Slf4j
public class MaxAllowedTempGenerator implements PointSequenceGenerator<MaxAllowedTemperature> {

    private static final Coefficients COEFFICIENTS = new Coefficients(List.of(
            new Coefficient(0, 10, 1.15),
            new Coefficient(11, 30, 1.1),
            new Coefficient(31, Integer.MAX_VALUE, 1.05)
    ));

    private final Integer time;
    private final StandardTemperature standardTemp;

    @Override
    public MaxAllowedTemperature generate() {
        val maxAllowedTemp = IntStream.range(0, time)
                .mapToObj(t -> new IntegerPoint(t,
                        (int) Math.round(standardTemp.getPoint(t).getValue() * COEFFICIENTS.getCoefficient(t))))
                .collect(Collectors.toList());

        return MaxAllowedTemperature.builder()
                .value(maxAllowedTemp)
                .smoothedValue(smoothMaxFunction(maxAllowedTemp))
                .build();
    }

}