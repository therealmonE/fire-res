package io.github.therealmone.fireres.gui.synchronizer.impl;

import io.github.therealmone.fireres.gui.synchronizer.ChartSynchronizer;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lombok.val;

import static io.github.therealmone.fireres.gui.model.ElementIds.THIRD_THERMOCOUPLE_GROUP_MEAN_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.THIRD_THERMOCOUPLE_GROUP_THERMOCOUPLE_MAX_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.THIRD_THERMOCOUPLE_GROUP_THERMOCOUPLE_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.util.ChartUtils.addPointsToSeries;

public class ThirdThermocoupleGroupChartSynchronizer implements ChartSynchronizer<UnheatedSurfaceReport> {

    public void synchronize(LineChart<Number, Number> chart, UnheatedSurfaceReport report) {
        chart.getData().clear();

        addThermocoupleTemperatureLine(chart, report);
        addThermocoupleBoundLine(chart, report);
        addMeanTemperatureLine(chart, report);
    }

    private void addThermocoupleTemperatureLine(LineChart<Number, Number> chart, UnheatedSurfaceReport report) {
        for (int i = 0; i < report.getThirdGroup().getThermocoupleTemperatures().size(); i++) {

            val thermocoupleSeries = new XYChart.Series<Number, Number>();
            val thermocoupleTemperature = report.getThirdGroup().getThermocoupleTemperatures().get(i);

            thermocoupleSeries.setName("Термопара - " + (i + 1));
            addPointsToSeries(thermocoupleSeries, thermocoupleTemperature);
            chart.getData().add(thermocoupleSeries);
            thermocoupleSeries.getNode().setId(THIRD_THERMOCOUPLE_GROUP_THERMOCOUPLE_TEMPERATURE_LINE);
        }
    }

    private void addThermocoupleBoundLine(LineChart<Number, Number> chart, UnheatedSurfaceReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Предельное значение температуры термопар");
        addPointsToSeries(series, report.getThirdGroup().getThermocoupleBound());
        chart.getData().add(series);
        series.getNode().setId(THIRD_THERMOCOUPLE_GROUP_THERMOCOUPLE_MAX_TEMPERATURE_LINE);
    }

    private void addMeanTemperatureLine(LineChart<Number, Number> chart, UnheatedSurfaceReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Среднее значение температуры");
        addPointsToSeries(series, report.getThirdGroup().getMeanTemperature());
        chart.getData().add(series);
        series.getNode().setId(THIRD_THERMOCOUPLE_GROUP_MEAN_TEMPERATURE_LINE);
    }
}
