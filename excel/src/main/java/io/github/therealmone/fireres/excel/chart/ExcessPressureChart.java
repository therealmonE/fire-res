package io.github.therealmone.fireres.excel.chart;

import io.github.therealmone.fireres.excel.column.Column;

import java.util.List;

public class ExcessPressureChart extends AbstractExcelChart {

    private static final Integer HEIGHT = 50;
    private static final Integer WIDTH = 25;

    private final String title = "Избыточное давление";
    private String valueAxisTitle = "Избыточное давление";

    public ExcessPressureChart(Integer time, List<Column> columns) {
        super(time, columns, HEIGHT, WIDTH);
    }

    @Override
    protected String getTitle() {
        return title;
    }

    @Override
    public String getValueAxisTitle() {
        return valueAxisTitle;
    }

    public void setValueAxisTitle(String valueAxisTitle) {
        this.valueAxisTitle = valueAxisTitle;
    }

    @Override
    protected boolean isSmoothed() {
        return false;
    }
}
