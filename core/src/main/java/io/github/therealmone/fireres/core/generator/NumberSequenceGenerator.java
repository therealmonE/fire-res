package io.github.therealmone.fireres.core.generator;

import io.github.therealmone.fireres.core.model.NumberSequence;

public interface NumberSequenceGenerator<T extends NumberSequence> {

    T generate();

}
