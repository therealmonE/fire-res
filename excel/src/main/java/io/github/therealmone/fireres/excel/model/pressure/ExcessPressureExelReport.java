package io.github.therealmone.fireres.excel.model.pressure;

import io.github.therealmone.fireres.core.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excel.chart.ExcelChart;
import io.github.therealmone.fireres.excel.chart.ExcessPressureChart;
import io.github.therealmone.fireres.excel.model.Column;
import io.github.therealmone.fireres.excel.model.ExcelReport;
import io.github.therealmone.fireres.excel.model.TimeColumn;
import lombok.Getter;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class ExcessPressureExelReport implements ExcelReport {

    private final Integer time;

    @Getter
    private final List<Column> data;
    private final Double basePressure;

    public ExcessPressureExelReport(Integer time, ExcessPressureReport report, Double basePressure) {
        this.time = time;
        this.data = createData(report, time, basePressure);
        this.basePressure = basePressure;
    }

    protected List<Column> createData(ExcessPressureReport excessPressureReport, Integer time, Double basePressure) {
        val columns = new ArrayList<Column>();

        columns.add(new TimeColumn(time));
        columns.add(new MaxAllowedPressureColumn(excessPressureReport.getMaxAllowedPressure()));
        columns.add(new MinAllowedPressureColumn(excessPressureReport.getMinAllowedPressure()));

        val samples = excessPressureReport.getSamples();

        for (int s = 0; s < samples.size(); s++) {
            val sample = samples.get(s);
            columns.add(new PressureColumn(s + 1, sample.getPressure(), basePressure));
        }

        for (int s = 0; s < samples.size(); s++) {
            val sample = samples.get(s);
            val delta = sample.getPressure();
            columns.add(new DeltaColumn(s + 1, delta));
        }

        return columns;
    }

    @Override
    public ExcelChart getChart() {
        return new ExcessPressureChart(time, getData(), basePressure);
    }
}
