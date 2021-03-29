package io.github.therealmone.fireres.firemode.model;

import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.List;

public class StandardTemperature extends IntegerPointSequence {

    @Getter
    @Nullable
    private final Integer temperatureMaintainingTime;

    public StandardTemperature(List<IntegerPoint> value, @Nullable Integer temperatureMaintainingTime) {
        super(value);
        this.temperatureMaintainingTime = temperatureMaintainingTime;
    }
}
