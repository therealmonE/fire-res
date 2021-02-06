package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.synchronizer.impl.ExcessPressureChartSynchronizer;
import io.github.therealmone.fireres.gui.synchronizer.impl.FireModeChartSynchronizer;
import io.github.therealmone.fireres.gui.synchronizer.impl.FirstThermocoupleGroupChartSynchronizer;
import io.github.therealmone.fireres.gui.synchronizer.impl.HeatFlowChartSynchronizer;
import io.github.therealmone.fireres.gui.synchronizer.impl.SecondThermocoupleGroupChartSynchronizer;
import io.github.therealmone.fireres.gui.synchronizer.impl.ThirdThermocoupleGroupChartSynchronizer;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import javafx.scene.chart.LineChart;

public class ChartsSynchronizationServiceImpl implements ChartsSynchronizationService {

    @Inject
    private FireModeChartSynchronizer fireModeChartSynchronizer;

    @Inject
    private ExcessPressureChartSynchronizer excessPressureChartSynchronizer;

    @Inject
    private HeatFlowChartSynchronizer heatFlowChartSynchronizer;

    @Inject
    private FirstThermocoupleGroupChartSynchronizer firstThermocoupleGroupChartSynchronizer;

    @Inject
    private SecondThermocoupleGroupChartSynchronizer secondThermocoupleGroupChartSynchronizer;

    @Inject
    private ThirdThermocoupleGroupChartSynchronizer thirdThermocoupleGroupChartSynchronizer;

    @Override
    public void syncFireModeChart(LineChart<Number, Number> chart, FireModeReport report) {
        fireModeChartSynchronizer.synchronize(chart, report);
    }

    @Override
    public void syncExcessPressureChart(LineChart<Number, Number> chart, ExcessPressureReport report) {
        excessPressureChartSynchronizer.synchronize(chart, report);
    }

    @Override
    public void syncHeatFlowChart(LineChart<Number, Number> chart, HeatFlowReport report) {
        heatFlowChartSynchronizer.synchronize(chart, report);
    }

    @Override
    public void syncFirstThermocoupleGroupChart(LineChart<Number, Number> chart, UnheatedSurfaceReport report) {
        firstThermocoupleGroupChartSynchronizer.synchronize(chart, report);
    }

    @Override
    public void syncSecondThermocoupleGroupChart(LineChart<Number, Number> chart, UnheatedSurfaceReport report) {
        secondThermocoupleGroupChartSynchronizer.synchronize(chart, report);
    }

    @Override
    public void syncThirdThermocoupleGroupChart(LineChart<Number, Number> chart, UnheatedSurfaceReport report) {
        thirdThermocoupleGroupChartSynchronizer.synchronize(chart, report);
    }


}
