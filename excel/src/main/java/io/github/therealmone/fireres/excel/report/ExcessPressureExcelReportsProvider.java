package io.github.therealmone.fireres.excel.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.excel.chart.ExcessPressureChart;
import io.github.therealmone.fireres.excel.column.Column;
import io.github.therealmone.fireres.excel.column.TimeColumn;
import io.github.therealmone.fireres.excel.column.excess.pressure.DeltaColumn;
import io.github.therealmone.fireres.excel.column.excess.pressure.MaxAllowedPressureColumn;
import io.github.therealmone.fireres.excel.column.excess.pressure.MinAllowedPressureColumn;
import io.github.therealmone.fireres.excel.column.excess.pressure.PressureColumn;
import io.github.therealmone.fireres.excess.pressure.model.ExcessPressureSample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExcessPressureExcelReportsProvider implements ExcelReportsProvider {

    @Inject
    private ExcessPressureReport excessPressureReport;

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public List<ExcelReport> get() {
        val time = generationProperties.getGeneral().getTime();

        return excessPressureReport.getSamples().stream()
                .map(sample -> {
                    val basePressure = sample.getBasePressure();
                    val index = excessPressureReport.getSamples().indexOf(sample);
                    val data = createSampleData(sample, index);

                    return ExcelReport.builder()
                            .data(data)
                            .chart(new ExcessPressureChart(time, data, basePressure))
                            .build();
                })
                .collect(Collectors.toList());
    }

    private List<Column> createSampleData(ExcessPressureSample sample, Integer index) {
        val time = generationProperties.getGeneral().getTime();
        val basePressure = sample.getBasePressure();
        val columns = new ArrayList<Column>();

        columns.add(new TimeColumn(time));
        columns.add(new MaxAllowedPressureColumn(sample.getMaxAllowedPressure()));
        columns.add(new MinAllowedPressureColumn(sample.getMinAllowedPressure()));
        columns.add(new PressureColumn(index + 1, sample.getPressure(), basePressure));
        columns.add(new DeltaColumn(index + 1, sample.getPressure()));


        return columns;
    }

}
