package io.github.therealmone.fireres.excel.model;

import io.github.therealmone.fireres.core.model.firemode.ThermocoupleTemperature;

public class ThermocoupleTemperatureColumn extends PointSequenceColumn {

    private static final String HEADER = "ТП";

    public ThermocoupleTemperatureColumn(Integer index, ThermocoupleTemperature thermocoupleTemperature) {
        super(HEADER + index, false, thermocoupleTemperature);
    }
}
