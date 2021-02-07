package io.github.therealmone.fireres.gui.controller.unheated.surface;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.first.thermocouple.group.FirstThermocoupleGroupPaneController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.second.thermocouple.group.SecondThermocoupleGroupPaneController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.third.thermocouple.group.ThirdThermocoupleGroupPaneController;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnheatedSurfacePaneController extends AbstractController implements ReportContainer {

    private UnheatedSurfaceReport report;

    @FXML
    @ChildController
    private FirstThermocoupleGroupPaneController firstThermocoupleGroupPaneController;

    @FXML
    @ChildController
    private SecondThermocoupleGroupPaneController secondThermocoupleGroupPaneController;

    @FXML
    @ChildController
    private ThirdThermocoupleGroupPaneController thirdThermocoupleGroupPaneController;

    @ParentController
    private SampleTabController sampleTabController;

    @Inject
    private UnheatedSurfaceService unheatedSurfaceService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    public Sample getSample() {
        return sampleTabController.getSample();
    }

    @Override
    protected void initialize() {
        firstThermocoupleGroupPaneController.setUnheatedSurfacePaneController(this);
        secondThermocoupleGroupPaneController.setUnheatedSurfacePaneController(this);
        thirdThermocoupleGroupPaneController.setUnheatedSurfacePaneController(this);
    }

    @Override
    public void postConstruct() {
        firstThermocoupleGroupPaneController.postConstruct();
        secondThermocoupleGroupPaneController.postConstruct();
        thirdThermocoupleGroupPaneController.postConstruct();

        this.report = unheatedSurfaceService.createReport(getSample());

        chartsSynchronizationService.syncFirstThermocoupleGroupChart(
                firstThermocoupleGroupPaneController
                        .getFirstThermocoupleGroupChartController()
                        .getFirstThermocoupleGroupChart(), report);

        chartsSynchronizationService.syncSecondThermocoupleGroupChart(
                secondThermocoupleGroupPaneController
                        .getSecondThermocoupleGroupChartController()
                        .getSecondThermocoupleGroupChart(), report);

        chartsSynchronizationService.syncThirdThermocoupleGroupChart(
                thirdThermocoupleGroupPaneController
                        .getThirdThermocoupleGroupChartController()
                        .getThirdThermocoupleGroupChart(), report);
    }
}
