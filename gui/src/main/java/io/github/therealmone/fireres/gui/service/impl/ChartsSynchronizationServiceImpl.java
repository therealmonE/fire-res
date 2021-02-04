package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.synchronizer.impl.FireModeChartSynchronizer;
import javafx.scene.chart.LineChart;

public class ChartsSynchronizationServiceImpl implements ChartsSynchronizationService {

    @Inject
    private FireModeChartSynchronizer fireModeChartSynchronizer;

    @Override
    public void syncFireModeChart(LineChart<Number, Number> chart, FireModeReport report) {
        fireModeChartSynchronizer.synchronize(chart, report);
    }

}
