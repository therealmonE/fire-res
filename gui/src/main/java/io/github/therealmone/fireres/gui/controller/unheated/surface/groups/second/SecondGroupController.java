package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.second;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceSecondGroupService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecondGroupController extends AbstractController implements UnheatedSurfaceReportContainer {

    @FXML
    @ChildController
    private SecondGroupParamsController secondGroupParamsController;

    @FXML
    @ChildController
    private FunctionParamsController functionParamsController;

    @FXML
    @ChildController
    private SecondGroupChartController secondGroupChartController;

    @ParentController
    private UnheatedSurfaceController unheatedSurfaceController;

    @Inject
    private UnheatedSurfaceSecondGroupService unheatedSurfaceSecondGroupService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    public Sample getSample() {
        return unheatedSurfaceController.getSample();
    }

    @Override
    protected void initialize() {
        secondGroupParamsController.setSecondGroupController(this);
        secondGroupChartController.setSecondGroupController(this);

        functionParamsController.setParentController(this);
        functionParamsController.setInterpolationService(unheatedSurfaceSecondGroupService);

        functionParamsController.setPropertiesMapper(props ->
                props.getReportPropertiesByClass(UnheatedSurfaceProperties.class).orElseThrow().getSecondGroup());

        functionParamsController.setPostReportUpdateAction(() ->
                chartsSynchronizationService.syncSecondThermocoupleGroupChart(
                        secondGroupChartController.getSecondGroupChart(), getReport()));
    }

    @Override
    public void postConstruct() {
        secondGroupParamsController.postConstruct();
        functionParamsController.postConstruct();
    }

    @Override
    public UnheatedSurfaceReport getReport() {
        return unheatedSurfaceController.getReport();
    }
}
