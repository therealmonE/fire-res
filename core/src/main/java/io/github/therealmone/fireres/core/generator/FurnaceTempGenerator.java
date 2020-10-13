package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.model.FurnaceTemperature;
import io.github.therealmone.fireres.core.model.StandardTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class FurnaceTempGenerator implements NumberSequenceGenerator<FurnaceTemperature> {

    private final Integer t0;
    private final StandardTemperature standardTemp;

    @Override
    public FurnaceTemperature generate() {
        log.info("Generating furnace temperature with t0: {}, standard temperature: {}", t0, standardTemp);
        val furnaceTemp = standardTemp.getValue().stream()
                .map(i -> t0 + i)
                .collect(Collectors.toList());

        log.info("Generated furnace temperature: {}", furnaceTemp);
        return new FurnaceTemperature(furnaceTemp);
    }

}
