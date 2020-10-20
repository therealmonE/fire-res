package io.github.therealmone.fireres.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public abstract class AbstractPointSequence implements PointSequence {

    private final List<Point> value;

}
