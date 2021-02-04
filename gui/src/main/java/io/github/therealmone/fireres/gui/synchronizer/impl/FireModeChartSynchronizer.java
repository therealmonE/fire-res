package io.github.therealmone.fireres.gui.synchronizer.impl;

import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.gui.synchronizer.ChartSynchronizer;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lombok.val;

import static io.github.therealmone.fireres.gui.model.ElementIds.FIREMODE_MAX_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIREMODE_MEAN_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIREMODE_MIN_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIREMODE_STANDARD_TEMPERATURE_LINE;

public class FireModeChartSynchronizer implements ChartSynchronizer<FireModeReport> {

    @Override
    public void synchronize(LineChart<Number, Number> chart, FireModeReport report) {
        chart.getData().clear();

        addThermocoupleMeanTemperatureLine(chart, report);
        addMinAllowedTemperatureLine(chart, report);
        addMaxAllowedTemperatureLine(chart, report);
        addStandardTemperatureLine(chart, report);
    }

    private void addStandardTemperatureLine(LineChart<Number, Number> chart, FireModeReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Стандартный режим пожара");
        report.getStandardTemperature().getValue()
                .forEach(point -> series.getData().add(new XYChart.Data<>(point.getTime(), point.getValue())));

        chart.getData().add(series);
        series.getNode().setId(FIREMODE_STANDARD_TEMPERATURE_LINE);
    }

    private void addMaxAllowedTemperatureLine(LineChart<Number, Number> chart, FireModeReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Максимальный допуск температуры");
        report.getMaxAllowedTemperature().getValue()
                .forEach(point -> series.getData().add(new XYChart.Data<>(point.getTime(), point.getValue())));

        chart.getData().add(series);
        series.getNode().setId(FIREMODE_MAX_TEMPERATURE_LINE);
    }

    private void addMinAllowedTemperatureLine(LineChart<Number, Number> chart, FireModeReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Минимальный допуск температуры");
        report.getMinAllowedTemperature().getValue()
                .forEach(point -> series.getData().add(new XYChart.Data<>(point.getTime(), point.getValue())));

        chart.getData().add(series);
        series.getNode().setId(FIREMODE_MIN_TEMPERATURE_LINE);
    }

    private void addThermocoupleMeanTemperatureLine(LineChart<Number, Number> chart, FireModeReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Средняя температура");
        report.getThermocoupleMeanTemperature().getValue()
                .forEach(point -> series.getData().add(new XYChart.Data<>(point.getTime(), point.getValue())));

        chart.getData().add(series);
        series.getNode().setId(FIREMODE_MEAN_TEMPERATURE_LINE);
    }

}
