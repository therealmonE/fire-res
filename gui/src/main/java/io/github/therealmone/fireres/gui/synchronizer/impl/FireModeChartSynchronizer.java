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
import static io.github.therealmone.fireres.gui.util.ChartUtils.addPointsToSeries;

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
        addPointsToSeries(series, report.getStandardTemperature());
        chart.getData().add(series);
        series.getNode().setId(FIREMODE_STANDARD_TEMPERATURE_LINE);
    }

    private void addMaxAllowedTemperatureLine(LineChart<Number, Number> chart, FireModeReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Максимальный допуск температуры");
        addPointsToSeries(series, report.getMaxAllowedTemperature());
        chart.getData().add(series);
        series.getNode().setId(FIREMODE_MAX_TEMPERATURE_LINE);
    }

    private void addMinAllowedTemperatureLine(LineChart<Number, Number> chart, FireModeReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Минимальный допуск температуры");
        addPointsToSeries(series, report.getMinAllowedTemperature());
        chart.getData().add(series);
        series.getNode().setId(FIREMODE_MIN_TEMPERATURE_LINE);
    }

    private void addThermocoupleMeanTemperatureLine(LineChart<Number, Number> chart, FireModeReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Средняя температура");
        addPointsToSeries(series, report.getThermocoupleMeanTemperature());
        chart.getData().add(series);
        series.getNode().setId(FIREMODE_MEAN_TEMPERATURE_LINE);
    }

}
