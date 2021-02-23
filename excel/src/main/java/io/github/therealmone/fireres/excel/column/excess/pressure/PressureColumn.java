package io.github.therealmone.fireres.excel.column.excess.pressure;

import io.github.therealmone.fireres.core.model.DoublePoint;
import io.github.therealmone.fireres.core.model.DoublePointSequence;
import io.github.therealmone.fireres.excess.pressure.model.Pressure;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;
import java.util.stream.Collectors;

public class PressureColumn extends PointSequenceColumn{

    private static final String HEADER = "P - %s";

    public PressureColumn(String sampleName, Pressure pressure, Double basePressure) {
        super(String.format(HEADER, sampleName), true,
                new DoublePointSequence(pressure.getValue().stream()
                        .map(p -> new DoublePoint(p.getTime(), basePressure + p.getNormalizedValue()))
                        .collect(Collectors.toList()))
        );
    }
}
