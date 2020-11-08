package io.github.therealmone.fireres.excel.model;

import io.github.therealmone.fireres.core.model.firemode.FurnaceTemperature;

public class FurnaceTemperatureColumn extends PointSequenceColumn {

    private static final String HEADER = "Т";

    public FurnaceTemperatureColumn(FurnaceTemperature points) {
        super(HEADER, false, points);
    }
}
