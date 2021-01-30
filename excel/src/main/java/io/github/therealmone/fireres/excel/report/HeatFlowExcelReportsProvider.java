package io.github.therealmone.fireres.excel.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.excel.chart.HeatFlowChart;
import io.github.therealmone.fireres.excel.column.Column;
import io.github.therealmone.fireres.excel.column.TimeColumn;
import io.github.therealmone.fireres.excel.column.heat.flow.HeatFlowBoundColumn;
import io.github.therealmone.fireres.excel.column.heat.flow.HeatFlowMeanTemperatureColumn;
import io.github.therealmone.fireres.excel.column.heat.flow.HeatFlowThermocoupleColumn;
import io.github.therealmone.fireres.heatflow.model.HeatFlowSample;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HeatFlowExcelReportsProvider implements ExcelReportsProvider {

    @Inject
    private HeatFlowReport report;

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public List<ExcelReport> get() {
        val time = generationProperties.getGeneral().getTime();

        return report.getSamples().stream()
                .map(sample -> {
                    val data = createSampleData(sample);
                    val index = report.getSamples().indexOf(sample);

                    return ExcelReport.builder()
                            .data(data)
                            .chart(new HeatFlowChart(time, data, index + 1))
                            .build();
                })
                .collect(Collectors.toList());
    }

    protected List<Column> createSampleData(HeatFlowSample sample) {
        val time = generationProperties.getGeneral().getTime();
        val columns = new ArrayList<Column>();

        columns.add(new TimeColumn(time));
        columns.add(new HeatFlowBoundColumn(sample.getBound()));
        columns.add(new HeatFlowMeanTemperatureColumn(sample.getMeanTemperature()));

        val sensorTemperatures = sample.getSensorTemperatures();
        for (int t = 0; t < sensorTemperatures.size(); t++) {
            val sensorTemperature = sensorTemperatures.get(t);

            columns.add(new HeatFlowThermocoupleColumn(t, sensorTemperature));
        }

        return columns;
    }
}