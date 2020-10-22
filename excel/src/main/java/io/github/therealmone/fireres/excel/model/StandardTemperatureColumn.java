package io.github.therealmone.fireres.excel.model;

import io.github.therealmone.fireres.core.model.StandardTemperature;

public class StandardTemperatureColumn extends PointSequenceColumn {

    private static final String HEADER = "Тст.пож.";

    public StandardTemperatureColumn(StandardTemperature standardTemperature) {
        super(HEADER, standardTemperature);
    }
}
