package io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.second.thermocouple.group;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfacePaneController;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceSecondGroupService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecondThermocoupleGroupPaneController extends AbstractController implements ReportContainer {

    @FXML
    @ChildController
    private SecondThermocoupleGroupParamsController secondThermocoupleGroupParamsController;

    @FXML
    @ChildController
    private FunctionParamsController functionParamsController;

    @FXML
    @ChildController
    private SecondThermocoupleGroupChartController secondThermocoupleGroupChartController;

    @ParentController
    private UnheatedSurfacePaneController unheatedSurfacePaneController;

    @Inject
    private UnheatedSurfaceSecondGroupService unheatedSurfaceSecondGroupService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    public Sample getSample() {
        return unheatedSurfacePaneController.getSample();
    }

    @Override
    protected void initialize() {
        secondThermocoupleGroupParamsController.setSecondThermocoupleGroupPaneController(this);
        secondThermocoupleGroupChartController.setSecondThermocoupleGroupPaneController(this);

        functionParamsController.setParentController(this);
        functionParamsController.setInterpolationService(unheatedSurfaceSecondGroupService);
        functionParamsController.setPropertiesMapper(sampleProperties -> sampleProperties.getUnheatedSurface().getSecondGroup());
        functionParamsController.setPostReportUpdateAction(() ->
                chartsSynchronizationService.syncSecondThermocoupleGroupChart(
                        secondThermocoupleGroupChartController.getSecondThermocoupleGroupChart(), (UnheatedSurfaceReport) getReport()));
    }

    @Override
    public void postConstruct() {
        secondThermocoupleGroupParamsController.postConstruct();
        functionParamsController.postConstruct();
    }

    @Override
    public Report getReport() {
        return unheatedSurfacePaneController.getReport();
    }
}
