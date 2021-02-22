package io.github.therealmone.fireres.gui.controller.excess.pressure;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.StackPane;

@LoadableComponent("/component/excess-pressure/excessPressureChart.fxml")
public class ExcessPressureChart extends AbstractComponent<StackPane>
        implements ExcessPressureReportContainer, ChartContainer {

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
        chartsSynchronizationService.syncExcessPressureChart(chart, getReport());
    }

    @Override
    public ExcessPressureReport getReport() {
        return ((ExcessPressure) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return this;
    }

    @Override
    public Sample getSample() {
        return ((ExcessPressure) getParent()).getSample();
    }
}
