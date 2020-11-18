package io.github.therealmone.fireres.excel.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.annotation.BasePressure;
import io.github.therealmone.fireres.core.annotation.Time;
import io.github.therealmone.fireres.excel.chart.ExcessPressureChart;
import io.github.therealmone.fireres.excel.model.Column;
import io.github.therealmone.fireres.excel.model.ExcelReport;
import io.github.therealmone.fireres.excel.model.ExcelReports;
import io.github.therealmone.fireres.excel.model.firemode.TimeColumn;
import io.github.therealmone.fireres.excel.model.pressure.DeltaColumn;
import io.github.therealmone.fireres.excel.model.pressure.MaxAllowedPressureColumn;
import io.github.therealmone.fireres.excel.model.pressure.MinAllowedPressureColumn;
import io.github.therealmone.fireres.excel.model.pressure.PressureColumn;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class ExcessPressureExcelReportsProvider implements ExcelReportsProvider {

    @Inject
    private ExcessPressureReport excessPressureReport;

    @Inject
    @Time
    private Integer time;

    @Inject
    @BasePressure
    private Double basePressure;

    @Override
    public ExcelReports get() {
        val data = createData();

        return new ExcelReports(List.of(ExcelReport.builder()
                .data(data)
                .chart(new ExcessPressureChart(time, data, basePressure))
                .build()));
    }

    private List<Column> createData() {
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

}
