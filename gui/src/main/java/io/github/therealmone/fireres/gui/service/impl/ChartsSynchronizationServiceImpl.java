package io.github.therealmone.fireres.gui.service.impl;

import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lombok.val;

public class ChartsSynchronizationServiceImpl implements ChartsSynchronizationService {

    @Override
    public void syncFireModeChart(LineChart<Number, Number> chart, FireModeReport report) {
        chart.getData().clear();

        val series = new XYChart.Series<Number, Number>();

        //todo: показывать остальные графики

        series.setName("Средняя температура");
        report.getThermocoupleMeanTemperature().getValue()
                .forEach(point -> series.getData().add(new XYChart.Data<>(point.getTime(), point.getValue())));

        chart.getData().add(series);
    }

}
