package io.github.therealmone.fireres.excel.column.firemode;

import io.github.therealmone.fireres.firemode.model.FurnaceTemperature;
import io.github.therealmone.fireres.excel.column.PointSequenceColumn;

public class FurnaceTemperatureColumn extends PointSequenceColumn {

    private static final String HEADER = "Т - %s";

    public FurnaceTemperatureColumn(String sampleName, FurnaceTemperature points) {
        super(String.format(HEADER, sampleName), false, points);
    }
}
