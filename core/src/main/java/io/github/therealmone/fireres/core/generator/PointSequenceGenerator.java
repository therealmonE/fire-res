package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.model.point.TemperaturePoint;
import io.github.therealmone.fireres.core.model.sequence.PointSequence;

public interface PointSequenceGenerator<T extends PointSequence<TemperaturePoint>> {

    T generate();

}
