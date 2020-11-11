package io.github.therealmone.fireres.core.generator.firemode;

import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.firemode.FurnaceTemperature;
import io.github.therealmone.fireres.core.model.firemode.StandardTemperature;
import io.github.therealmone.fireres.core.model.point.IntegerPoint;
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
        val furnaceTemp = IntStream.range(0, time)
                .mapToObj(t -> new IntegerPoint(t,
                        t0 + standardTemp.getPoint(t).getValue()))
                .collect(Collectors.toList());

        return new FurnaceTemperature(furnaceTemp);
    }

}
