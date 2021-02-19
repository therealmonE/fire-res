package io.github.therealmone.fireres.heatflow.generator;

import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.core.model.DoublePoint;
import io.github.therealmone.fireres.core.model.DoublePointSequence;
import io.github.therealmone.fireres.heatflow.model.HeatFlowBound;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPoint;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;

@RequiredArgsConstructor
public class HeatFlowBoundGenerator implements PointSequenceGenerator<HeatFlowBound> {

    private final Integer time;
    private final Double bound;

    @Override
    public HeatFlowBound generate() {
        return new HeatFlowBound(IntStream.range(0, time)
                .mapToObj(t -> new HeatFlowPoint(t, bound))
                .collect(Collectors.toList()));
    }

}
