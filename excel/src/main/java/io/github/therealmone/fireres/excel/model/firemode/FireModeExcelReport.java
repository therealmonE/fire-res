package io.github.therealmone.fireres.excel.model.firemode;

import io.github.therealmone.fireres.core.firemode.report.FireModeReport;
import io.github.therealmone.fireres.excel.chart.ExcelChart;
import io.github.therealmone.fireres.excel.chart.FireModeChart;
import io.github.therealmone.fireres.excel.model.Column;
import io.github.therealmone.fireres.excel.model.ExcelReport;
import lombok.Getter;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class FireModeExcelReport implements ExcelReport {

    private final Integer time;

    @Getter
    private final List<Column> data;

    public FireModeExcelReport(Integer time, Integer environmentTemperature, FireModeReport report) {
        this.data = createData(report, time, environmentTemperature);
        this.time = time;
    }

    protected List<Column> createData(FireModeReport fireModeReport, Integer time, Integer environmentTemperature) {
        val columns = new ArrayList<Column>();

        columns.add(new TimeColumn(time));
        columns.add(new EnvTempColumn(time, environmentTemperature));
        columns.add(new FurnaceTemperatureColumn(fireModeReport.getFurnaceTemperature()));
        columns.add(new MinAllowedTemperatureColumn(fireModeReport.getMinAllowedTemperature()));
        columns.add(new MaxAllowedTemperatureColumn(fireModeReport.getMaxAllowedTemperature()));
        columns.add(new EightTimeColumn(time));
        columns.add(new StandardTemperatureColumn(fireModeReport.getStandardTemperature()));

        val samples = fireModeReport.getSamples();

        for (int s = 0; s < samples.size(); s++) {
            val sample = samples.get(s);
            val thermocoupleTemperatures = sample.getThermocoupleTemperatures();

            for (int t = 0; t < thermocoupleTemperatures.size(); t++) {
                val thermocoupleTemperature = thermocoupleTemperatures.get(t);
                columns.add(new ThermocoupleTemperatureColumn(t + 1, thermocoupleTemperature));
            }

            columns.add(new ThermocouplesMeanTemperatureColumn(s + 1, sample.getThermocoupleMeanTemperature()));
        }

        return columns;
    }

    @Override
    public ExcelChart getChart() {
        return new FireModeChart(time, getData());
    }
}
