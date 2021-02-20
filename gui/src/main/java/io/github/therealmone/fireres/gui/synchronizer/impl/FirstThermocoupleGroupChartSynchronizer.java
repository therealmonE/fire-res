package io.github.therealmone.fireres.gui.synchronizer.impl;

import io.github.therealmone.fireres.gui.synchronizer.ChartSynchronizer;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lombok.val;

import static io.github.therealmone.fireres.gui.model.ElementIds.DEFAULT_MEAN_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.DEFAULT_MEAN_LINE_LEGEND_SYMBOL;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIRST_THERMOCOUPLE_GROUP_MEAN_BOUND_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIRST_THERMOCOUPLE_GROUP_THERMOCOUPLE_MAX_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIRST_THERMOCOUPLE_GROUP_THERMOCOUPLE_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.util.ChartUtils.addLegendSymbolId;
import static io.github.therealmone.fireres.gui.util.ChartUtils.addPointsToSeries;

public class FirstThermocoupleGroupChartSynchronizer implements ChartSynchronizer<UnheatedSurfaceReport> {

    public static final String THERMOCOUPLE_TEXT = "Термопара - ";
    public static final String MAX_THERMOCOUPLE_TEMPERATURE_TEXT = "Предельное значение температуры термопар";
    public static final String MAX_MEAN_TEMPERATURE_TEXT = "Предельное значение средней температуры";
    public static final String MEAN_TEMPERATURE_TEXT = "Среднее значение температуры";

    @Override
    public void synchronize(LineChart<Number, Number> chart, UnheatedSurfaceReport report) {
        chart.getData().clear();

        addThermocoupleTemperatureLines(chart, report);
        addMeanTemperatureLine(chart, report);
        addMeanBoundLine(chart, report);
        addThermocoupleBoundLine(chart, report);

        addLegendSymbolId(chart, MEAN_TEMPERATURE_TEXT, DEFAULT_MEAN_LINE_LEGEND_SYMBOL);
    }

    private void addThermocoupleTemperatureLines(LineChart<Number, Number> chart, UnheatedSurfaceReport report) {
        for (int i = 0; i < report.getFirstGroup().getThermocoupleTemperatures().size(); i++) {

            val thermocoupleSeries = new XYChart.Series<Number, Number>();
            val thermocoupleTemperature = report.getFirstGroup().getThermocoupleTemperatures().get(i);

            thermocoupleSeries.setName(THERMOCOUPLE_TEXT + (i + 1));
            addPointsToSeries(thermocoupleSeries, thermocoupleTemperature);
            chart.getData().add(thermocoupleSeries);
            thermocoupleSeries.getNode().setId(FIRST_THERMOCOUPLE_GROUP_THERMOCOUPLE_TEMPERATURE_LINE);
        }
    }

    private void addThermocoupleBoundLine(LineChart<Number, Number> chart, UnheatedSurfaceReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName(MAX_THERMOCOUPLE_TEMPERATURE_TEXT);
        addPointsToSeries(series, report.getFirstGroup().getThermocoupleBound());
        chart.getData().add(series);
        series.getNode().setId(FIRST_THERMOCOUPLE_GROUP_THERMOCOUPLE_MAX_TEMPERATURE_LINE);
    }

    private void addMeanBoundLine(LineChart<Number, Number> chart, UnheatedSurfaceReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName(MAX_MEAN_TEMPERATURE_TEXT);
        addPointsToSeries(series, report.getFirstGroup().getMeanBound());
        chart.getData().add(series);
        series.getNode().setId(FIRST_THERMOCOUPLE_GROUP_MEAN_BOUND_TEMPERATURE_LINE);
    }

    private void addMeanTemperatureLine(LineChart<Number, Number> chart, UnheatedSurfaceReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName(MEAN_TEMPERATURE_TEXT);
        addPointsToSeries(series, report.getFirstGroup().getMeanTemperature());
        chart.getData().add(series);
        series.getNode().setId(DEFAULT_MEAN_LINE);
    }

}
