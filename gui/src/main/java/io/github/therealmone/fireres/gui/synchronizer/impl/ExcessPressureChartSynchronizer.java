package io.github.therealmone.fireres.gui.synchronizer.impl;

import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.gui.synchronizer.ChartSynchronizer;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lombok.val;

import static io.github.therealmone.fireres.gui.model.ElementIds.EXCESS_PRESSURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.EXCESS_PRESSURE_MAX_ALLOWED_PRESSURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.EXCESS_PRESSURE_MIN_ALLOWED_PRESSURE_LINE;
import static io.github.therealmone.fireres.gui.util.ChartUtils.addPointsToSeries;

public class ExcessPressureChartSynchronizer implements ChartSynchronizer<ExcessPressureReport> {

    private static final String Y_AXIS_LABEL_TEMPLATE = "Избыточное давление, %s±Δ";

    @Override
    public void synchronize(LineChart<Number, Number> chart, ExcessPressureReport report) {
        chart.getData().clear();

        addMinAllowedPressureLine(chart, report);
        addMaxAllowedPressureLine(chart, report);
        addPressureLine(chart, report);
        setXAxisName(chart, report);
    }

    private void addMinAllowedPressureLine(LineChart<Number, Number> chart, ExcessPressureReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Минимальный допуск избыточного давления");
        addPointsToSeries(series, report.getMinAllowedPressure());
        chart.getData().add(series);
        series.getNode().setId(EXCESS_PRESSURE_MIN_ALLOWED_PRESSURE_LINE);
    }

    private void addMaxAllowedPressureLine(LineChart<Number, Number> chart, ExcessPressureReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Максимальный допуск избыточного давления");
        addPointsToSeries(series, report.getMaxAllowedPressure());
        chart.getData().add(series);
        series.getNode().setId(EXCESS_PRESSURE_MAX_ALLOWED_PRESSURE_LINE);
    }

    private void addPressureLine(LineChart<Number, Number> chart, ExcessPressureReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Избыточное давление");
        addPointsToSeries(series, report.getPressure());
        chart.getData().add(series);
        series.getNode().setId(EXCESS_PRESSURE_LINE);
    }

    private void setXAxisName(LineChart<Number, Number> chart, ExcessPressureReport report) {
        chart.getYAxis().setLabel(String.format(Y_AXIS_LABEL_TEMPLATE, report.getBasePressure()));
    }

}
