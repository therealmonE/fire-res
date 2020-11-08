package io.github.therealmone.fireres.core.model.point;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IntegerPoint extends Point<Integer> {

    @Builder
    public IntegerPoint(Integer time, Integer value) {
        super(time, value);
    }

    @Override
    public IntegerPoint normalize(Integer shift) {
        //already normalized
        return this;
    }

}
