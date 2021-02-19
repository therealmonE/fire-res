package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.StackPane;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FirstGroupChartController extends AbstractController implements UnheatedSurfaceReportContainer, ChartContainer {

    @FXML
    private StackPane firstGroupChartStackPane;

    private FirstGroupController firstGroupController;

    @FXML
    private LineChart<Number, Number> firstGroupChart;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    public UnheatedSurfaceReport getReport() {
        return firstGroupController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return this;
    }

    @Override
    public Sample getSample() {
        return firstGroupController.getSample();
    }

    @Override
    public LineChart getChart() {
        return firstGroupChart;
    }

    @Override
    public StackPane getStackPane() {
        return firstGroupChartStackPane;
    }

    @Override
    public void synchronizeChart() {
        chartsSynchronizationService.syncFirstThermocoupleGroupChart(firstGroupChart, getReport());
    }
}
