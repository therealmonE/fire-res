package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.FurnaceTemperature;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.StandardTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
public class FurnaceTempGenerator implements PointSequenceGenerator<FurnaceTemperature> {

    private final Integer t0;
    private final StandardTemperature standardTemp;

    @Override
    public FurnaceTemperature generate() {
        log.info("Generating furnace temperature with t0: {}, standard temperature: {}", t0, standardTemp.getValue());

        val furnaceTemp = IntStream.range(0, standardTemp.getValue().size())
                .mapToObj(t -> new Point(t,
                        t0 + standardTemp.getValue().get(t).getTemperature()))
                .collect(Collectors.toList());

        log.info("Generated furnace temperature: {}", furnaceTemp);
        return new FurnaceTemperature(furnaceTemp);
    }

}
