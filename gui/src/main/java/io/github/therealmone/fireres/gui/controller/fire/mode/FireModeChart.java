package io.github.therealmone.fireres.gui.controller.fire.mode;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.StackPane;

@LoadableComponent("/component/fire-mode/fireModeChart.fxml")
public class FireModeChart extends AbstractComponent<StackPane>
        implements FireModeReportContainer, ChartContainer {

    @FXML
    private LineChart<Number, Number> chart;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    public LineChart getChart() {
        return chart;
    }

    @Override
    public StackPane getStackPane() {
        return getComponent();
    }

    @Override
    public void synchronizeChart() {
        chartsSynchronizationService.syncFireModeChart(chart, getReport());
    }

    @Override
    public FireModeReport getReport() {
        return ((FireMode) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return this;
    }

    @Override
    public Sample getSample() {
        return ((FireMode) getParent()).getSample();
    }
}
