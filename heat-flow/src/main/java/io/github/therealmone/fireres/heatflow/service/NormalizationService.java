package io.github.therealmone.fireres.heatflow.service;

import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPointSequence;

public interface NormalizationService {

    IntegerPointSequence disnormalize(HeatFlowPointSequence doublePointSequence);

    HeatFlowPointSequence normalize(IntegerPointSequence integerPointSequence);

}
