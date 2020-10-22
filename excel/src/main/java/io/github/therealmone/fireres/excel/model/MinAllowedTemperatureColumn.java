package io.github.therealmone.fireres.excel.model;

import io.github.therealmone.fireres.core.model.MinAllowedTemperature;

public class MinAllowedTemperatureColumn extends PointSequenceColumn {

    private static final String HEADER = "Тмин";

    public MinAllowedTemperatureColumn(MinAllowedTemperature minAllowedTemperature) {
        super(HEADER, minAllowedTemperature);
    }
}
