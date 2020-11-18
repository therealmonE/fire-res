package io.github.therealmone.fireres.excel.model.firemode;

import io.github.therealmone.fireres.firemode.model.FurnaceTemperature;
import io.github.therealmone.fireres.excel.model.PointSequenceColumn;

public class FurnaceTemperatureColumn extends PointSequenceColumn {

    private static final String HEADER = "Т";

    public FurnaceTemperatureColumn(FurnaceTemperature points) {
        super(HEADER, false, points);
    }
}
