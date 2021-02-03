package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.firemode.report.FireModeReport;
import javafx.scene.chart.LineChart;

public interface ChartsSynchronizationService {

    void syncFireModeChart(LineChart<Number, Number> chart, FireModeReport report);

}
