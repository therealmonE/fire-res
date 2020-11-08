package io.github.therealmone.fireres.core.generator.impl;

import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.firemode.FurnaceTemperature;
import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import io.github.therealmone.fireres.core.model.firemode.StandardTemperature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
public class FurnaceTempGenerator implements PointSequenceGenerator<FurnaceTemperature> {

    private final Integer time;
    private final Integer t0;
    private final StandardTemperature standardTemp;

    @Override
    public FurnaceTemperature generate() {
        log.trace("Generating furnace temperature with t0: {}, standard temperature: {}", t0, standardTemp.getValue());

        val furnaceTemp = IntStream.range(0, time)
                .mapToObj(t -> new TemperaturePoint(t,
                        t0 + standardTemp.getTemperature(t)))
                .collect(Collectors.toList());

        log.trace("Generated furnace temperature: {}", furnaceTemp);
        return new FurnaceTemperature(furnaceTemp);
    }

}
