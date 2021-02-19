package io.github.therealmone.fireres.heatflow.service.impl;

import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPoint;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPointSequence;
import io.github.therealmone.fireres.heatflow.service.NormalizationService;

import java.util.stream.Collectors;

public class NormalizationServiceImpl implements NormalizationService {

    public static final Integer NORMALIZATION_MULTIPLIER = 1000;

    @Override
    public IntegerPointSequence disnormalize(HeatFlowPointSequence doublePointSequence) {
        return new IntegerPointSequence(doublePointSequence.getValue().stream()
                .map(p -> new IntegerPoint(p.getTime(), p.getIntValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public HeatFlowPointSequence normalize(IntegerPointSequence integerPointSequence) {
        return new HeatFlowPointSequence(integerPointSequence.getValue().stream()
                .map(p -> new HeatFlowPoint(p.getTime(), p.getValue() / 1000d))
                .collect(Collectors.toList()));
    }
}
