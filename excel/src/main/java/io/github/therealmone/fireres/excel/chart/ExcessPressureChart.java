package io.github.therealmone.fireres.excel.chart;

import io.github.therealmone.fireres.excel.model.Column;

import java.util.List;

public class ExcessPressureChart extends AbstractExcelChart {

    private static final Integer HEIGHT = 50;
    private static final Integer WIDTH = 25;

    public ExcessPressureChart(Integer time, List<Column> columns) {
        super(time, columns, HEIGHT, WIDTH);
    }
}
