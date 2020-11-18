package io.github.therealmone.fireres.core.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public abstract class PointSequence<T extends Point<?>> {

    private final List<T> value;

    public IntegerPointSequence normalize(Integer shift) {
        return new IntegerPointSequence(getValue().stream()
                .map(p -> p.normalize(shift))
                .collect(Collectors.toList()));
    }

    public T getPoint(Integer time) {
        return getValue().stream()
                .filter(point -> point.getTime().equals(time))
                .findAny()
                .orElseThrow();
    }

}
