package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import io.github.therealmone.fireres.core.model.sequence.PointSequence;

import java.util.List;

public interface MultiplePointSequencesGenerator<T extends PointSequence<TemperaturePoint>> {

    List<T> generate();

}
