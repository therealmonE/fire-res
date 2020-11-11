package io.github.therealmone.fireres.core.common.generator;

import io.github.therealmone.fireres.core.common.model.PointSequence;

import java.util.List;

public interface MultiplePointSequencesGenerator<T extends PointSequence<?>> {

    List<T> generate();

}
