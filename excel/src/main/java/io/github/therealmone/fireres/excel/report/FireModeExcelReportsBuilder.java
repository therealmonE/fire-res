package io.github.therealmone.fireres.excel.report;

import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.excel.chart.FireModeChart;
import io.github.therealmone.fireres.excel.column.Column;
import io.github.therealmone.fireres.excel.column.firemode.EightTimeColumn;
import io.github.therealmone.fireres.excel.column.firemode.EnvTempColumn;
import io.github.therealmone.fireres.excel.column.firemode.FurnaceTemperatureColumn;
import io.github.therealmone.fireres.excel.column.firemode.MaxAllowedTemperatureColumn;
import io.github.therealmone.fireres.excel.column.firemode.MinAllowedTemperatureColumn;
import io.github.therealmone.fireres.excel.column.firemode.StandardTemperatureColumn;
import io.github.therealmone.fireres.excel.column.firemode.ThermocoupleTemperatureColumn;
import io.github.therealmone.fireres.excel.column.firemode.ThermocouplesMeanTemperatureColumn;
import io.github.therealmone.fireres.excel.column.TimeColumn;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class FireModeExcelReportsBuilder implements ExcelReportsBuilder {

    @Override
    public List<ExcelReport> build(GeneralProperties generalProperties, List<Report> reports) {
        val time = generalProperties.getTime();
        val data = createData(generalProperties, reports);

        return List.of(ExcelReport.builder()
                .data(data)
                .chart(new FireModeChart(time, data))
                .build());
    }

    protected List<Column> createData(GeneralProperties generalProperties, List<Report> reports) {
        val columns = new ArrayList<Column>();

        val time = generalProperties.getTime();
        val environmentTemperature = generalProperties.getEnvironmentTemperature();

        columns.add(new TimeColumn(time));
        columns.add(new EightTimeColumn(time));
        columns.add(new EnvTempColumn(time, environmentTemperature));

        for (Report report : reports) {
            val fireModeReport = (FireModeReport) report;
            val sampleName = report.getSample().getSampleProperties().getName();

            columns.add(new FurnaceTemperatureColumn(sampleName, fireModeReport.getFurnaceTemperature()));
            columns.add(new MinAllowedTemperatureColumn(sampleName, fireModeReport.getMinAllowedTemperature()));
            columns.add(new MaxAllowedTemperatureColumn(sampleName, fireModeReport.getMaxAllowedTemperature()));
            columns.add(new StandardTemperatureColumn(sampleName, fireModeReport.getStandardTemperature()));

            val thermocoupleTemperatures = fireModeReport.getThermocoupleTemperatures();

            for (int t = 0; t < thermocoupleTemperatures.size(); t++) {
                val thermocoupleTemperature = thermocoupleTemperatures.get(t);
                columns.add(new ThermocoupleTemperatureColumn(sampleName, t + 1, thermocoupleTemperature));
            }

            columns.add(new ThermocouplesMeanTemperatureColumn(sampleName, fireModeReport.getThermocoupleMeanTemperature()));
        }

        return columns;
    }

}
