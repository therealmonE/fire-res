package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.model.NumberSequence;

import java.util.List;

public interface MultipleNumberSequencesGenerator<T extends NumberSequence> {

    List<T> generate();

}
