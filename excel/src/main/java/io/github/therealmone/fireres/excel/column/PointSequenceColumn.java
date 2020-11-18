package io.github.therealmone.fireres.excel.column;

import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.PointSequence;

import java.util.Comparator;
import java.util.stream.Collectors;

public abstract class PointSequenceColumn extends Column {

    public PointSequenceColumn(String header, boolean bordered, PointSequence<?> points) {
        super(header, bordered, points.getValue().stream()
                .sorted(Comparator.comparing(Point::getTime))
                .map(Point::getValue)
                .collect(Collectors.toList()));
    }

}
