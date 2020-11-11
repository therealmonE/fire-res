package io.github.therealmone.fireres.core.common.generator;

import io.github.therealmone.fireres.core.common.model.PointSequence;

public interface PointSequenceGenerator<T extends PointSequence<?>> {

    T generate();

}
