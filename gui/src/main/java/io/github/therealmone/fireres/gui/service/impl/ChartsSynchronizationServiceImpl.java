package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.synchronizer.impl.ExcessPressureChartSynchronizer;
import io.github.therealmone.fireres.gui.synchronizer.impl.FireModeChartSynchronizer;
import javafx.scene.chart.LineChart;

public class ChartsSynchronizationServiceImpl implements ChartsSynchronizationService {

    @Inject
    private FireModeChartSynchronizer fireModeChartSynchronizer;

    @Inject
    private ExcessPressureChartSynchronizer excessPressureChartSynchronizer;

    @Override
    public void syncFireModeChart(LineChart<Number, Number> chart, FireModeReport report) {
        fireModeChartSynchronizer.synchronize(chart, report);
    }

    @Override
    public void syncExcessPressureChart(LineChart<Number, Number> chart, ExcessPressureReport report) {
        excessPressureChartSynchronizer.synchronize(chart, report);
    }

}
