package io.github.therealmone.fireres.gui.controller.excess.pressure;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExcessPressurePaneController extends AbstractController implements ReportContainer {

    private ExcessPressureReport report;

    @Inject
    private ExcessPressureService excessPressureService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @FXML
    @ChildController
    private ExcessPressureParamsController excessPressureParamsController;

    @FXML
    @ChildController
    private ExcessPressureChartController excessPressureChartController;

    @ParentController
    private SampleTabController sampleTabController;

    @Override
    public Sample getSample() {
        return sampleTabController.getSample();
    }

    @Override
    protected void initialize() {
        excessPressureParamsController.setExcessPressurePaneController(this);
        excessPressureChartController.setExcessPressurePaneController(this);
    }

    @Override
    public void postConstruct() {
        excessPressureParamsController.postConstruct();

        this.report = excessPressureService.createReport(getSample());
        chartsSynchronizationService.syncExcessPressureChart(excessPressureChartController.getExcessPressureChart(), report);
    }

    @Override
    public Report getReport() {
        return report;
    }
}
