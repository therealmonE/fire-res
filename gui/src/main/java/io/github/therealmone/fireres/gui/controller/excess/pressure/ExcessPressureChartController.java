package io.github.therealmone.fireres.gui.controller.excess.pressure;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
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
public class ExcessPressureChartController extends AbstractController implements ExcessPressureReportContainer, ChartContainer {

    @FXML
    private StackPane excessPressureChartStackPane;

    @FXML
    private LineChart<Number, Number> excessPressureChart;

    private ExcessPressureController excessPressureController;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    public LineChart getChart() {
        return excessPressureChart;
    }

    @Override
    public StackPane getStackPane() {
        return excessPressureChartStackPane;
    }

    @Override
    public void synchronizeChart() {
        chartsSynchronizationService.syncExcessPressureChart(excessPressureChart, getReport());
    }

    @Override
    public ExcessPressureReport getReport() {
        return excessPressureController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return this;
    }

    @Override
    public Sample getSample() {
        return excessPressureController.getSample();
    }
}
