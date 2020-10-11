package io.github.therealmone.fireres.core.generator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
public class StandardTempGenerator implements NumberSequenceGenerator {

    @Min(value = 0, message = "T0 must be greater than 0")
    private final Integer t0;

    @Min(value = 0, message = "Time must be greater than 0")
    private final Integer time;

    @Override
    public List<Integer> generate() {
        log.info("Generating standard temperature with  t0: {}, time: {}", t0, time);
        val standardTemp = IntStream.range(0, time + 1)
                .map(i -> (int) Math.round(345 * Math.log10(8 * i + 1)))
                .boxed()
                .collect(Collectors.toList());

        standardTemp.set(0, t0);
        log.info("Generated standard temperature: {}", standardTemp);
        return standardTemp;
    }

}
