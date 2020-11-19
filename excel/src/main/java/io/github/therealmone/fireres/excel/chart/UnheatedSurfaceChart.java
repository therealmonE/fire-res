package io.github.therealmone.fireres.excel.chart;

import io.github.therealmone.fireres.excel.column.Column;

import java.util.List;

public class UnheatedSurfaceChart extends AbstractExcelChart {

    private static final Integer HEIGHT = 50;
    private static final Integer WIDTH = 25;

    public UnheatedSurfaceChart(Integer time, List<Column> columns) {
        super(time, columns, HEIGHT, WIDTH);
    }

    @Override
    protected String getValueAxisTitle() {
        return "Температура, оС";
    }

    @Override
    protected boolean isSmoothed() {
        return true;
    }

}