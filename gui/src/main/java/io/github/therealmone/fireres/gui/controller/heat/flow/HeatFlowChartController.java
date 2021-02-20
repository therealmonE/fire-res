package io.github.therealmone.fireres.gui.controller.heat.flow;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.StackPane;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HeatFlowChartController extends AbstractController implements HeatFlowReportContainer, ChartContainer {

    @FXML
    private StackPane heatFlowChartStackPane;

    @FXML
    private LineChart<Number, Number> heatFlowChart;

    private HeatFlowController heatFlowController;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    public HeatFlowReport getReport() {
        return heatFlowController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return this;
    }

    @Override
    public Sample getSample() {
        return heatFlowController.getSample();
    }

    @Override
    public LineChart getChart() {
        return heatFlowChart;
    }

    @Override
    public StackPane getStackPane() {
        return heatFlowChartStackPane;
    }

    @Override
    public void synchronizeChart() {
        chartsSynchronizationService.syncHeatFlowChart(heatFlowChart, getReport());
    }
}
