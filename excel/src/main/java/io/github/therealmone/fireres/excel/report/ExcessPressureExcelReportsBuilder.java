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
import java.util.Locale;

public class ExcessPressureExcelReportsBuilder implements ExcelReportsBuilder {

    @Override
    public List<ExcelReport> build(GeneralProperties generalProperties, List<Report> reports) {
        val time = generalProperties.getTime();
        val data = createData(generalProperties, reports);
        val chart = new ExcessPressureChart(time, data);

        if (basePressuresAreSame(reports)) {
            chart.setValueAxisTitle(String.format(Locale.US, "%s - %.1f±ΔПа",
                    chart.getValueAxisTitle(),
                    ((ExcessPressureReport) reports.get(0)).getBasePressure()));
        }

        return Collections.singletonList(ExcelReport.builder()
                .data(data)
                .chart(chart)
                .build());
    }

    private boolean basePressuresAreSame(List<Report> reports) {
        val basePressure = ((ExcessPressureReport) reports.get(0)).getBasePressure();

        return reports.stream()
                .allMatch(report -> ((ExcessPressureReport) report).getBasePressure().equals(basePressure));
    }

    private List<Column> createData(GeneralProperties generalProperties, List<Report> reports) {
        val time = generalProperties.getTime();
        val columns = new ArrayList<Column>();

        columns.add(new TimeColumn(time));

        for (Report report : reports) {
            val excessPressureReport = (ExcessPressureReport) report;
            val basePressure = excessPressureReport.getBasePressure();
            val sampleName = report.getSample().getSampleProperties().getName();

            columns.add(new MaxAllowedPressureColumn(sampleName, excessPressureReport.getMaxAllowedPressure()));
            columns.add(new MinAllowedPressureColumn(sampleName, excessPressureReport.getMinAllowedPressure()));
            columns.add(new PressureColumn(sampleName, excessPressureReport.getPressure(), basePressure));
            columns.add(new DeltaColumn(sampleName, excessPressureReport.getPressure()));
        }

        return columns;
    }

}
