package io.github.therealmone.fireres.excel.report;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.excel.chart.ExcessPressureChart;
import io.github.therealmone.fireres.excel.column.Column;
import io.github.therealmone.fireres.excel.column.TimeColumn;
import io.github.therealmone.fireres.excel.column.excess.pressure.DeltaColumn;
import io.github.therealmone.fireres.excel.column.excess.pressure.MaxAllowedPressureColumn;
import io.github.therealmone.fireres.excel.column.excess.pressure.MinAllowedPressureColumn;
import io.github.therealmone.fireres.excel.column.excess.pressure.PressureColumn;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExcessPressureExcelReportsBuilder implements ExcelReportsBuilder {

    @Override
    public List<ExcelReport> build(GeneralProperties generalProperties, Report report) {
        val excessPressureReport = (ExcessPressureReport) report;
        val time = generalProperties.getTime();
        val basePressure = excessPressureReport.getBasePressure();
        val data = createData(generalProperties, excessPressureReport);

        return Collections.singletonList(ExcelReport.builder()
                .data(data)
                .chart(new ExcessPressureChart(time, data, basePressure))
                .build());
    }

    private List<Column> createData(GeneralProperties generalProperties, ExcessPressureReport report) {
        val time = generalProperties.getTime();
        val basePressure = report.getBasePressure();
        val columns = new ArrayList<Column>();

        columns.add(new TimeColumn(time));
        columns.add(new MaxAllowedPressureColumn(report.getMaxAllowedPressure()));
        columns.add(new MinAllowedPressureColumn(report.getMinAllowedPressure()));
        columns.add(new PressureColumn(report.getPressure(), basePressure));
        columns.add(new DeltaColumn(report.getPressure()));

        return columns;
    }

}
