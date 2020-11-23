package io.github.therealmone.fireres.excel.chart;

import io.github.therealmone.fireres.excel.column.Column;

import java.util.List;

public class HeatFlowChart extends AbstractExcelChart {

    private static final Integer HEIGHT = 50;
    private static final Integer WIDTH = 25;

    public HeatFlowChart(Integer time, List<Column> columns) {
        super(time, columns, HEIGHT, WIDTH);
    }

    @Override
    protected String getValueAxisTitle() {
        return "Тепловой поток, Вт/м^2";
    }

    @Override
    protected boolean isSmoothed() {
        return true;
    }

}