package io.github.therealmone.fireres.excel.mapper;

import io.github.therealmone.fireres.core.report.Report;
import io.github.therealmone.fireres.excel.model.EightTimeColumn;
import io.github.therealmone.fireres.excel.model.EnvTempColumn;
import io.github.therealmone.fireres.excel.model.ExcelReport;
import io.github.therealmone.fireres.excel.model.FurnaceTemperatureColumn;
import io.github.therealmone.fireres.excel.model.MaxAllowedTemperatureColumn;
import io.github.therealmone.fireres.excel.model.MinAllowedTemperatureColumn;
import io.github.therealmone.fireres.excel.model.StandardTemperatureColumn;
import io.github.therealmone.fireres.excel.model.ThermocoupleTemperatureColumn;
import io.github.therealmone.fireres.excel.model.ThermocouplesMeanTemperatureColumn;
import io.github.therealmone.fireres.excel.model.TimeColumn;
import lombok.val;


public class ExcelReportMapper {

    public static ExcelReport mapToExcelReport(Report report) {
        val excelReport = new ExcelReport();

        val time = report.getTime();
        val envTemp = report.getEnvironmentTemperature();

        excelReport.setMaxRows(time);

        excelReport.getColumns().add(new TimeColumn(time));
        excelReport.getColumns().add(new EnvTempColumn(time, envTemp));
        excelReport.getColumns().add(new FurnaceTemperatureColumn(report.getFurnaceTemperature()));
        excelReport.getColumns().add(new MinAllowedTemperatureColumn(report.getMinAllowedTemperature()));
        excelReport.getColumns().add(new MaxAllowedTemperatureColumn(report.getMaxAllowedTemperature()));
        excelReport.getColumns().add(new EightTimeColumn(time));
        excelReport.getColumns().add(new StandardTemperatureColumn(report.getStandardTemperature()));

        val samples = report.getSamples();

        for (int s = 0; s < samples.size(); s++) {
            val sample = samples.get(s);
            val thermocoupleTemperatures = sample.getThermocoupleTemperatures();

            for (int t = 0; t < thermocoupleTemperatures.size(); t++) {
                val thermocoupleTemperature = thermocoupleTemperatures.get(t);
                excelReport.getColumns().add(new ThermocoupleTemperatureColumn(t, thermocoupleTemperature));
            }

            excelReport.getColumns().add(new ThermocouplesMeanTemperatureColumn(s, sample.getThermocoupleMeanTemperature()));
        }

        return excelReport;

    }

}
