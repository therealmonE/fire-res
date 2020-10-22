package io.github.therealmone.fireres.excel.model;

import io.github.therealmone.fireres.core.model.MaxAllowedTemperature;

public class MaxAllowedTemperatureColumn extends PointSequenceColumn {

    private static final String HEADER = "Тмакс";

    public MaxAllowedTemperatureColumn(MaxAllowedTemperature minAllowedTemperature) {
        super(HEADER, minAllowedTemperature);
    }
}
