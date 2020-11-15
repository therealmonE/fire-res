package io.github.therealmone.fireres.excel.chart;

import io.github.therealmone.fireres.excel.model.Column;

import java.util.List;

public class ExcessPressureChart extends AbstractExcelChart {

    private static final Integer HEIGHT = 50;
    private static final Integer WIDTH = 25;

    private final Double basePressure;

    public ExcessPressureChart(Integer time, List<Column> columns, Double basePressure) {
        super(time, columns, HEIGHT, WIDTH);
        this.basePressure = basePressure;
    }

    @Override
    protected String getValueAxisTitle() {
        return "Избыточное давление, " + basePressure + "±Δ";
    }

    @Override
    protected boolean isSmoothed() {
        return false;
    }
}
