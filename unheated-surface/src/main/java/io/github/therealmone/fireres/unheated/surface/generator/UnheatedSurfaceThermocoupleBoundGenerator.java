package io.github.therealmone.fireres.unheated.surface.generator;

import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceThermocoupleBound;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class UnheatedSurfaceThermocoupleBoundGenerator implements PointSequenceGenerator<UnheatedSurfaceThermocoupleBound> {

    private final Integer time;
    private final Integer t0;

    @Override
    public UnheatedSurfaceThermocoupleBound generate() {
        val thermocoupleBound = IntStream.range(0, time)
                .mapToObj(t -> new IntegerPoint(t, 180 + t0))
                .collect(Collectors.toList());

        return new UnheatedSurfaceThermocoupleBound(thermocoupleBound);
    }

}
