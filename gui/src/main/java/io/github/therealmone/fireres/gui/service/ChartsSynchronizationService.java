package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import javafx.scene.chart.LineChart;

public interface ChartsSynchronizationService {

    void syncFireModeChart(LineChart<Number, Number> chart, FireModeReport report);

    void syncExcessPressureChart(LineChart<Number, Number> chart, ExcessPressureReport report);

    void syncHeatFlowChart(LineChart<Number, Number> chart, HeatFlowReport report);

}
