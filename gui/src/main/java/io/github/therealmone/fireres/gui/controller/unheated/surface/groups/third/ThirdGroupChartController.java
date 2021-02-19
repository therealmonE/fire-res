package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third;

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
public class ThirdGroupChartController extends AbstractController implements UnheatedSurfaceReportContainer, ChartContainer {

    @FXML
    private StackPane thirdGroupChartStackPane;

    private ThirdGroupController thirdGroupController;

    @FXML
    private LineChart<Number, Number> thirdGroupChart;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    public UnheatedSurfaceReport getReport() {
        return thirdGroupController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return this;
    }

    @Override
    public Sample getSample() {
        return thirdGroupController.getSample();
    }

    @Override
    public LineChart getChart() {
        return thirdGroupChart;
    }

    @Override
    public StackPane getStackPane() {
        return thirdGroupChartStackPane;
    }

    @Override
    public void synchronizeChart() {
        chartsSynchronizationService.syncThirdThermocoupleGroupChart(thirdGroupChart, getReport());
    }
}
