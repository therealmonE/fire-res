package io.github.therealmone.fireres.core.model.sequence;

import io.github.therealmone.fireres.core.model.point.IntegerPoint;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class SmoothedIntegerPointSequence extends IntegerPointSequence {

    private final List<IntegerPoint> smoothedValue;

    public SmoothedIntegerPointSequence(List<IntegerPoint> value, List<IntegerPoint> smoothedValue) {
        super(value);
        this.smoothedValue = smoothedValue;
    }

    public IntegerPointSequence smoothed() {
        return new IntegerPointSequence(smoothedValue);
    }

}
