package io.github.therealmone.fireres.excel.column.excess.pressure;

import io.github.therealmone.fireres.core.model.DoublePoint;
import io.github.therealmone.fireres.core.model.DoublePointSequence;
import io.github.therealmone.fireres.excess.pressure.model.SamplePressure;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import java.util.stream.Collectors;

public class PressureColumn extends PointSequenceColumn{

    private static final String HEADER = "Обр.";

    public PressureColumn(Integer index, SamplePressure samplePressure, Double basePressure) {
        super(HEADER + index, false,
                new DoublePointSequence(samplePressure.getValue().stream()
                        .map(p -> new DoublePoint(p.getTime(), basePressure + p.getValue()))
                        .collect(Collectors.toList()))
        );
    }
}
