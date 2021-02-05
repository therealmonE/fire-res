package io.github.therealmone.fireres.gui.synchronizer.impl;

import io.github.therealmone.fireres.gui.synchronizer.ChartSynchronizer;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lombok.val;

import static io.github.therealmone.fireres.gui.model.ElementIds.HEAT_FLOW_MAX_ALLOWED_FLOW_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.HEAT_FLOW_MEAN_FLOW_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.HEAT_FLOW_SENSORS_LINE;
import static io.github.therealmone.fireres.gui.util.ChartUtils.addPointsToSeries;

public class HeatFlowChartSynchronizer implements ChartSynchronizer<HeatFlowReport> {

    @Override
    public void synchronize(LineChart<Number, Number> chart, HeatFlowReport report) {
        chart.getData().clear();

        addMaxAllowedHeatFlowLine(chart, report);
        addMeanHeatFlowLine(chart, report);
        addSensorsLine(chart, report);
    }

    private void addMaxAllowedHeatFlowLine(LineChart<Number, Number> chart, HeatFlowReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Предельное значение теплового потока");
        addPointsToSeries(series, report.getBound());
        chart.getData().add(series);
        series.getNode().setId(HEAT_FLOW_MAX_ALLOWED_FLOW_LINE);
    }

    private void addMeanHeatFlowLine(LineChart<Number, Number> chart, HeatFlowReport report) {
        val series = new XYChart.Series<Number, Number>();

        series.setName("Среднее значение теплового потока");
        addPointsToSeries(series, report.getMeanTemperature());
        chart.getData().add(series);
        series.getNode().setId(HEAT_FLOW_MEAN_FLOW_LINE);
    }

    private void addSensorsLine(LineChart<Number, Number> chart, HeatFlowReport report) {
        for (int i = 0; i < report.getSensorTemperatures().size(); i++) {
            val sensorSeries = new XYChart.Series<Number, Number>();
            val sensorTemperature = report.getSensorTemperatures().get(i);

            sensorSeries.setName("Приемник теплового потока - " + (i + 1));
            addPointsToSeries(sensorSeries, sensorTemperature);
            chart.getData().add(sensorSeries);
            sensorSeries.getNode().setId(HEAT_FLOW_SENSORS_LINE);

        }
    }

}
