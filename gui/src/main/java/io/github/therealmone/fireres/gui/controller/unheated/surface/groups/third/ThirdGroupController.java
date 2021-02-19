package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.IntegerPoint;
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
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceThirdGroupService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ThirdGroupController extends AbstractController implements UnheatedSurfaceReportContainer {

    @FXML
    @ChildController
    private ThirdGroupParamsController thirdGroupParamsController;

    @FXML
    @ChildController
    private ThirdGroupChartController thirdGroupChartController;

    @FXML
    @ChildController
    private FunctionParamsController functionParamsController;

    @ParentController
    private UnheatedSurfaceController unheatedSurfaceController;

    @Inject
    private UnheatedSurfaceThirdGroupService unheatedSurfaceThirdGroupService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    public Sample getSample() {
        return unheatedSurfaceController.getSample();
    }

    @Override
    protected void initialize() {
        thirdGroupParamsController.setThirdGroupController(this);
        thirdGroupChartController.setThirdGroupController(this);

        functionParamsController.setParentController(this);
        functionParamsController.setInterpolationService(unheatedSurfaceThirdGroupService);

        functionParamsController.setPropertiesMapper(props ->
                props.getReportPropertiesByClass(UnheatedSurfaceProperties.class).orElseThrow().getThirdGroup());

        functionParamsController.setPostReportUpdateAction(() ->
                chartsSynchronizationService.syncThirdThermocoupleGroupChart(
                        thirdGroupChartController.getThirdGroupChart(), getReport()));

        functionParamsController.setInterpolationPointConstructor((time, value) -> new IntegerPoint(time, value.intValue()));
    }

    @Override
    public void postConstruct() {
        thirdGroupParamsController.postConstruct();
        functionParamsController.postConstruct();
    }

    @Override
    public UnheatedSurfaceReport getReport() {
        return unheatedSurfaceController.getReport();
    }
}
