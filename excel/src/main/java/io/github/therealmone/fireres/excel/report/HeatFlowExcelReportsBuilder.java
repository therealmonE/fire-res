package io.github.therealmone.fireres.excel.report;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Report;
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
import java.util.stream.Collectors;

public class HeatFlowExcelReportsBuilder implements ExcelReportsBuilder {

    @Override
    public List<ExcelReport> build(GeneralProperties generalProperties, List<Report> reports) {
        val time = generalProperties.getTime();

        return reports.stream()
                .map(report -> {
                    val data = createData(generalProperties, ((HeatFlowReport) report));

                    return ExcelReport.builder()
                            .data(data)
                            .chart(new HeatFlowChart(time, data))
                            .build();
                })
                .collect(Collectors.toList());
    }

    protected List<Column> createData(GeneralProperties generalProperties, HeatFlowReport report) {
        val time = generalProperties.getTime();
        val columns = new ArrayList<Column>();

        columns.add(new TimeColumn(time));
        columns.add(new HeatFlowBoundColumn(report.getBound()));
        columns.add(new HeatFlowMeanTemperatureColumn(report.getMeanTemperature()));

        val sensorTemperatures = report.getSensorTemperatures();
        for (int t = 0; t < sensorTemperatures.size(); t++) {
            val sensorTemperature = sensorTemperatures.get(t);

            columns.add(new HeatFlowThermocoupleColumn(t, sensorTemperature));
        }

        return columns;
    }
}