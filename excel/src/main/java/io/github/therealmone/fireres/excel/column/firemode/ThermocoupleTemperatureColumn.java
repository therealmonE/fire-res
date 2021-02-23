package io.github.therealmone.fireres.excel.column.firemode;

import io.github.therealmone.fireres.firemode.model.ThermocoupleTemperature;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;

public class ThermocoupleTemperatureColumn extends PointSequenceColumn {

    private static final String HEADER = "ТП%d - %s";

    public ThermocoupleTemperatureColumn(String sampleName, Integer index, ThermocoupleTemperature thermocoupleTemperature) {
        super(String.format(HEADER, index, sampleName), false, thermocoupleTemperature);
    }
}
