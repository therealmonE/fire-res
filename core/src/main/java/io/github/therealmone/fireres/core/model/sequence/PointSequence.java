package io.github.therealmone.fireres.core.model.sequence;

import io.github.therealmone.fireres.core.model.point.IntegerPoint;
import io.github.therealmone.fireres.core.model.point.Point;

import java.util.List;
import java.util.stream.Collectors;

public interface PointSequence<T extends Point<?>> {

    List<T> getValue();

    default List<IntegerPoint> getNormalizedValue(Integer shift) {
        return getValue().stream()
                .map(p -> p.normalize(shift))
                .collect(Collectors.toList());
    }

}
