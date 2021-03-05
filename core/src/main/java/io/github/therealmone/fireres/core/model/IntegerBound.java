package io.github.therealmone.fireres.core.model;

import io.github.therealmone.fireres.core.utils.FunctionUtils;

import java.util.List;

public class IntegerBound extends IntegerPointSequence {
    public IntegerBound(List<IntegerPoint> value) {
        super(value);
    }

    public List<IntegerPoint> getShiftedValue(BoundShift<IntegerPoint> shift) {
        return FunctionUtils.calculateShiftedIntegerPoints(this.getValue(), shift);
    }
}
