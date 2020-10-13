package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.config.Coefficients;
import io.github.therealmone.fireres.core.model.MaxAllowedTemperature;
import io.github.therealmone.fireres.core.model.StandardTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
public class MaxAllowedTempGenerator implements NumberSequenceGenerator<MaxAllowedTemperature> {

    private final Coefficients coefficients;
    private final StandardTemperature standardTemp;

    @Override
    public MaxAllowedTemperature generate() {
        log.info("Generating maximum allowed temperature with coefficients: {}, standard temperature: {}",
                coefficients, standardTemp);

        val maxAllowedTemp = IntStream.range(0, standardTemp.getValue().size())
                .map(t -> (int) Math.round(standardTemp.getValue().get(t) * coefficients.getCoefficient(t).getValue()))
                .boxed()
                .collect(Collectors.toList());

        log.info("Generated maximum allowed temperature: {}", maxAllowedTemp);
        return new MaxAllowedTemperature(maxAllowedTemp);
    }

}
