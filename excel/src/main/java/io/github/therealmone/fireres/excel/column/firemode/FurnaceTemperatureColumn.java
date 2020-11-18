package io.github.therealmone.fireres.excel.column.firemode;

import io.github.therealmone.fireres.firemode.model.FurnaceTemperature;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;

public class FurnaceTemperatureColumn extends PointSequenceColumn {

    private static final String HEADER = "Т";

    public FurnaceTemperatureColumn(FurnaceTemperature points) {
        super(HEADER, false, points);
    }
}
