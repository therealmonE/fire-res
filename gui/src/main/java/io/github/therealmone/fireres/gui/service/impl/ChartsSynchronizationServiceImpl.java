package io.github.therealmone.fireres.gui.service.impl;

import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lombok.val;

import static io.github.therealmone.fireres.gui.model.ElementIds.FIREMODE_MAX_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIREMODE_MEAN_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIREMODE_MIN_TEMPERATURE_LINE;
import static io.github.therealmone.fireres.gui.model.ElementIds.FIREMODE_STANDARD_TEMPERATURE_LINE;

public class ChartsSynchronizationServiceImpl implements ChartsSynchronizationService {

    @Override
    public void syncFireModeChart(LineChart<Number, Number> chart, FireModeReport report) {
        chart.getData().clear();

        val series = new XYChart.Series<Number, Number>();
        val seriesMin = new XYChart.Series<Number, Number>();
        val seriesMax = new XYChart.Series<Number, Number>();
        val seriesStandard = new XYChart.Series<Number, Number>();

        series.setName("Средняя температура");
        report.getThermocoupleMeanTemperature().getValue()
                .forEach(point -> series.getData().add(new XYChart.Data<>(point.getTime(), point.getValue())));

        chart.getData().add(series);
        series.getNode().setId(FIREMODE_MEAN_TEMPERATURE_LINE);

        seriesMin.setName("Минимальный допуск температуры");
        report.getMinAllowedTemperature().getValue()
                .forEach(point -> seriesMin.getData().add(new XYChart.Data<>(point.getTime(), point.getValue())));

        chart.getData().add(seriesMin);
        seriesMin.getNode().setId(FIREMODE_MIN_TEMPERATURE_LINE);


        seriesMax.setName("Максимальный допуск температуры");
        report.getMaxAllowedTemperature().getValue()
                .forEach(point -> seriesMax.getData().add(new XYChart.Data<>(point.getTime(), point.getValue())));

        chart.getData().add(seriesMax);
        seriesMax.getNode().setId(FIREMODE_MAX_TEMPERATURE_LINE);

        seriesStandard.setName("Стандартный режим пожара");
        report.getStandardTemperature().getValue()
                .forEach(point -> seriesStandard.getData().add(new XYChart.Data<>(point.getTime(), point.getValue())));

        chart.getData().add(seriesStandard);
        seriesStandard.getNode().setId(FIREMODE_STANDARD_TEMPERATURE_LINE);
    }

}
