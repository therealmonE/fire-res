package io.github.therealmone.fireres.gui.controller.excess.pressure;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportInclusionChanger;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static io.github.therealmone.fireres.core.config.ReportType.EXCESS_PRESSURE;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExcessPressureController extends AbstractController implements ExcessPressureReportContainer, ReportInclusionChanger {

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

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public Sample getSample() {
        return sampleTabController.getSample();
    }

    @Override
    protected void initialize() {
        excessPressureParamsController.setExcessPressureController(this);
        excessPressureChartController.setExcessPressureController(this);
    }

    @Override
    public void postConstruct() {
        excessPressureParamsController.postConstruct();
    }

    @Override
    public void createReport() {
        this.report = excessPressureService.createReport(getSample());

        if (!generationProperties.getGeneral().getIncludedReports().contains(EXCESS_PRESSURE)) {
            excludeReport();
        }

        chartsSynchronizationService.syncExcessPressureChart(excessPressureChartController.getExcessPressureChart(), report);
    }

    @Override
    public void excludeReport() {
        getSample().removeReport(report);
        disableTab(sampleTabController.getExcessPressureTab());
        generationProperties.getGeneral().getIncludedReports().removeIf(EXCESS_PRESSURE::equals);
    }

    @Override
    public void includeReport() {
        getSample().putReport(report);
        enableTab(sampleTabController.getExcessPressureTab(), sampleTabController.getReportsTabPane());
        generationProperties.getGeneral().getIncludedReports().add(EXCESS_PRESSURE);
    }
}
