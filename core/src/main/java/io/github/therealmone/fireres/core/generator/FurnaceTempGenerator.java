package io.github.therealmone.fireres.core.generator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class FurnaceTempGenerator implements NumberSequenceGenerator {

    @Min(value = 0, message = "T0 must be greater than 0")
    private final Integer t0;

    @Size(min = 1, message = "Standard temperature can't be empty")
    private final List<Integer> standardTemp;

    @Override
    public List<Integer> generate() {
        log.info("Generating furnace temperature with t0: {}, standard temperature: {}", t0, standardTemp);
        val furnaceTemp = standardTemp.stream()
                .map(i -> t0 + i)
                .collect(Collectors.toList());

        log.info("Generated furnace temperature: {}", furnaceTemp);
        return furnaceTemp;
    }

}
