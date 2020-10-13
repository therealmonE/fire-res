package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.model.StandardTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
public class StandardTempGenerator implements NumberSequenceGenerator<StandardTemperature> {

    private final Integer t0;
    private final Integer time;

    @Override
    public StandardTemperature generate() {
        log.info("Generating standard temperature with  t0: {}, time: {}", t0, time);
        val standardTemp = IntStream.range(0, time + 1)
                .map(i -> (int) Math.round(345 * Math.log10(8 * i + 1)))
                .boxed()
                .collect(Collectors.toList());

        standardTemp.set(0, t0);
        log.info("Generated standard temperature: {}", standardTemp);
        return new StandardTemperature(standardTemp);
    }

}
