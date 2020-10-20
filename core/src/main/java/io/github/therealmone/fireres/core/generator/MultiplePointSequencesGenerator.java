package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.model.PointSequence;

import java.util.List;

public interface MultiplePointSequencesGenerator<T extends PointSequence> {

    List<T> generate();

}
