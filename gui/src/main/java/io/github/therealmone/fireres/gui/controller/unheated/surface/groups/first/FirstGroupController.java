package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceController;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceFirstGroupService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FirstGroupController extends AbstractController implements ReportContainer {

    @FXML
    @ChildController
    private FirstGroupParamsController firstGroupParamsController;

    @FXML
    @ChildController
    private FunctionParamsController functionParamsController;

    @FXML
    @ChildController
    private FirstGroupChartController firstGroupChartController;

    @ParentController
    private UnheatedSurfaceController unheatedSurfaceController;

    @Inject
    private UnheatedSurfaceFirstGroupService unheatedSurfaceFirstGroupService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    public Sample getSample() {
        return unheatedSurfaceController.getSample();
    }

    @Override
    protected void initialize() {
        firstGroupParamsController.setFirstGroupController(this);
        firstGroupChartController.setFirstGroupController(this);

        functionParamsController.setParentController(this);
        functionParamsController.setInterpolationService(unheatedSurfaceFirstGroupService);
        functionParamsController.setPropertiesMapper(sampleProperties -> sampleProperties.getUnheatedSurface().getFirstGroup());
        functionParamsController.setPostReportUpdateAction(() ->
                chartsSynchronizationService.syncFirstThermocoupleGroupChart(
                        firstGroupChartController.getFirstGroupChart(), (UnheatedSurfaceReport) getReport()));
    }

    @Override
    public void postConstruct() {
        firstGroupParamsController.postConstruct();
        functionParamsController.postConstruct();
    }

    @Override
    public Report getReport() {
        return unheatedSurfaceController.getReport();
    }
}
