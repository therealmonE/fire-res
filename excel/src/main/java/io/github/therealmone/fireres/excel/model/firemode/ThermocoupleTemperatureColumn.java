package io.github.therealmone.fireres.excel.model.firemode;

import io.github.therealmone.fireres.core.firemode.model.ThermocoupleTemperature;
import io.github.therealmone.fireres.excel.model.PointSequenceColumn;

public class ThermocoupleTemperatureColumn extends PointSequenceColumn {

    private static final String HEADER = "ТП";

    public ThermocoupleTemperatureColumn(Integer index, ThermocoupleTemperature thermocoupleTemperature) {
        super(HEADER + index, false, thermocoupleTemperature);
    }
}
