package io.github.therealmone.fireres.heatflow.service;

import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPoint;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPointSequence;

import java.util.List;

public interface NormalizationService {

    IntegerPointSequence disnormalize(List<HeatFlowPoint> heatFlowPoints);

    HeatFlowPointSequence normalize(IntegerPointSequence integerPointSequence);

}
