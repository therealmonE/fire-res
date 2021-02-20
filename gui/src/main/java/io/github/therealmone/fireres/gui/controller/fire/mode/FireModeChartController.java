package io.github.therealmone.fireres.gui.controller.fire.mode;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.StackPane;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FireModeChartController extends AbstractController implements FireModeReportContainer, ChartContainer {

    private FireModeController fireModeController;

    @FXML
    private LineChart<Number, Number> fireModeChart;

    @FXML
    private StackPane fireModeChartStackPane;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    protected void initialize() {
    }

    @Override
    public LineChart getChart() {
        return fireModeChart;
    }

    @Override
    public StackPane getStackPane() {
        return fireModeChartStackPane;
    }

    @Override
    public void synchronizeChart() {
        chartsSynchronizationService.syncFireModeChart(fireModeChart, getReport());
    }

    @Override
    public FireModeReport getReport() {
        return fireModeController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return this;
    }

    @Override
    public Sample getSample() {
        return fireModeController.getSample();
    }
}
