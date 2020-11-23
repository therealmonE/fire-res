package io.github.therealmone.fireres.excel.chart;

import io.github.therealmone.fireres.excel.column.Column;

import java.util.List;

public class HeatFlowChart extends AbstractExcelChart {

    private static final Integer HEIGHT = 50;
    private static final Integer WIDTH = 25;

    private final Integer index;

    public HeatFlowChart(Integer time, List<Column> columns, Integer index) {
        super(time, columns, HEIGHT, WIDTH);
        this.index = index;
    }

    @Override
    protected String getTitle() {
        return "Плотность теплового потока - Образец №" + index;
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