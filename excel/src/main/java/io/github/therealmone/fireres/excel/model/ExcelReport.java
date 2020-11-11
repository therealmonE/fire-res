package io.github.therealmone.fireres.excel.model;

import io.github.therealmone.fireres.excel.chart.ExcelChart;

import java.util.List;

public interface ExcelReport {

    List<Column> getData();

    ExcelChart getChart();

}
