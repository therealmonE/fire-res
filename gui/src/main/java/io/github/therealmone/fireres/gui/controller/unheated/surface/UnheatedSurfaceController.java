package io.github.therealmone.fireres.gui.controller.unheated.surface;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.ReportInclusionChanger;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first.FirstGroupController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.second.SecondGroupController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third.ThirdGroupController;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static io.github.therealmone.fireres.core.config.ReportType.UNHEATED_SURFACE;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnheatedSurfaceController extends AbstractController implements ReportContainer, ReportInclusionChanger {

    private static final Double SCROLL_VEL = 2d;

    private UnheatedSurfaceReport report;

    @FXML
    @ChildController
    private FirstGroupController firstGroupController;

    @FXML
    @ChildController
    private SecondGroupController secondGroupController;

    @FXML
    @ChildController
    private ThirdGroupController thirdGroupController;

    @ParentController
    private SampleTabController sampleTabController;

    @Inject
    private UnheatedSurfaceService unheatedSurfaceService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public Sample getSample() {
        return sampleTabController.getSample();
    }

    @Override
    protected void initialize() {
        firstGroupController.setUnheatedSurfaceController(this);
        secondGroupController.setUnheatedSurfaceController(this);
        thirdGroupController.setUnheatedSurfaceController(this);
    }

    @Override
    public void postConstruct() {
        firstGroupController.postConstruct();
        secondGroupController.postConstruct();
        thirdGroupController.postConstruct();
    }

    @Override
    public void createReport() {
        this.report = unheatedSurfaceService.createReport(getSample());

        if (!generationProperties.getGeneral().getIncludedReports().contains(UNHEATED_SURFACE)) {
            excludeReport();
        }

        chartsSynchronizationService.syncFirstThermocoupleGroupChart(
                firstGroupController
                        .getFirstGroupChartController()
                        .getFirstGroupChart(), report);

        chartsSynchronizationService.syncSecondThermocoupleGroupChart(
                secondGroupController
                        .getSecondGroupChartController()
                        .getSecondGroupChart(), report);

        chartsSynchronizationService.syncThirdThermocoupleGroupChart(
                thirdGroupController
                        .getThirdGroupChartController()
                        .getThirdGroupChart(), report);
    }

    @Override
    public void excludeReport() {
        getSample().removeReport(report);
        disableTab(sampleTabController.getUnheatedSurfaceTab());
        generationProperties.getGeneral().getIncludedReports().removeIf(UNHEATED_SURFACE::equals);
    }

    @Override
    public void includeReport() {
        getSample().putReport(report);
        enableTab(sampleTabController.getUnheatedSurfaceTab(), sampleTabController.getReportsTabPane());
        generationProperties.getGeneral().getIncludedReports().add(UNHEATED_SURFACE);
    }
}
