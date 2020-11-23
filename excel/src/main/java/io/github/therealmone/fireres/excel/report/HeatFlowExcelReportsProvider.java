package io.github.therealmone.fireres.excel.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.annotation.Time;
import io.github.therealmone.fireres.excel.chart.HeatFlowChart;
import io.github.therealmone.fireres.excel.column.Column;
import io.github.therealmone.fireres.excel.column.TimeColumn;
import io.github.therealmone.fireres.excel.column.heat.flow.HeatFlowBoundColumn;
import io.github.therealmone.fireres.excel.column.heat.flow.HeatFlowMeanTemperatureColumn;
import io.github.therealmone.fireres.excel.column.heat.flow.HeatFlowThermocoupleColumn;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;

import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class HeatFlowExcelReportsProvider implements ExcelReportsProvider {

    @Inject
    private HeatFlowReport report;

    @Inject
    @Time
    private Integer time;

    @Override
    public List<ExcelReport> get() {
        val data = createData();

        return List.of(ExcelReport.builder()
                .data(data)
                .chart(new HeatFlowChart(time, data))
                .build());
    }

    protected List<Column> createData() {
        val columns = new ArrayList<Column>();

        columns.add(new TimeColumn(time));

        val samples = report.getSamples();

        for (int s = 0; s < samples.size(); s++) {
            val sample = samples.get(s);

            columns.add(new HeatFlowBoundColumn(sample.getBound()));
            columns.add(new HeatFlowMeanTemperatureColumn(sample.getMeanTemperature()));

            val sensorTemperatures = sample.getSensorTemperatures();
            for (int t=0; t < sensorTemperatures.size(); t++ ){
                val sensorTemperature = sensorTemperatures.get(t);

                columns.add(new HeatFlowThermocoupleColumn(t, sensorTemperature));
            }
        }

        return columns;
    }
}