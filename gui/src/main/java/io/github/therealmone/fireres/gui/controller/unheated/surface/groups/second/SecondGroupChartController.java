package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.second;

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
public class SecondGroupChartController extends AbstractController implements UnheatedSurfaceReportContainer, ChartContainer {

    @FXML
    private StackPane secondGroupChartStackPane;

    private SecondGroupController secondGroupController;

    @FXML
    private LineChart<Number, Number> secondGroupChart;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    public UnheatedSurfaceReport getReport() {
        return secondGroupController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return this;
    }

    @Override
    public Sample getSample() {
        return secondGroupController.getSample();
    }

    @Override
    public LineChart getChart() {
        return secondGroupChart;
    }

    @Override
    public StackPane getStackPane() {
        return secondGroupChartStackPane;
    }

    @Override
    public void synchronizeChart() {
        chartsSynchronizationService.syncSecondThermocoupleGroupChart(secondGroupChart, getReport());
    }
}
