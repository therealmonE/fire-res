package io.github.therealmone.fireres.core.model;

import io.github.therealmone.fireres.core.utils.FunctionUtils;

import java.util.List;

public class DoubleBound extends DoublePointSequence {
    public DoubleBound(List<DoublePoint> value) {
        super(value);
    }

    public List<DoublePoint> getShiftedValue(BoundShift<DoublePoint> shift) {
        return FunctionUtils.calculateShiftedDoublePoints(this.getValue(), shift);
    }
}
