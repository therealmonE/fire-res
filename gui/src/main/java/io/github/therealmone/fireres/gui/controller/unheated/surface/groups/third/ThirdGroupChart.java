package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.StackPane;

@LoadableComponent("/component/unheated-surface/groups/third/thirdGroupChart.fxml")
public class ThirdGroupChart extends AbstractComponent<StackPane>
        implements UnheatedSurfaceReportContainer, ChartContainer {

    @FXML
    private LineChart<Number, Number> chart;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    public UnheatedSurfaceReport getReport() {
        return ((ThirdGroup) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return this;
    }

    @Override
    public Sample getSample() {
        return ((ThirdGroup) getParent()).getSample();
    }

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
        chartsSynchronizationService.syncThirdThermocoupleGroupChart(chart, getReport());
    }
}
