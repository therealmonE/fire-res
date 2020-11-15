package io.github.therealmone.fireres.core.unheated.generator;

import io.github.therealmone.fireres.core.common.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.common.model.IntegerPoint;
import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceMeanBound;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class UnheatedSurfaceMeanBoundGenerator implements PointSequenceGenerator<UnheatedSurfaceMeanBound> {

    private final Integer time;
    private final Integer t0;

    @Override
    public UnheatedSurfaceMeanBound generate() {
        val meanBound = IntStream.range(0, time)
                .mapToObj(t -> new IntegerPoint(t, 140 + t0))
                .collect(Collectors.toList());

        return new UnheatedSurfaceMeanBound(meanBound);
    }

}
