package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.model.PointSequence;

public interface PointSequenceGenerator<T extends PointSequence> {

    T generate();

}
