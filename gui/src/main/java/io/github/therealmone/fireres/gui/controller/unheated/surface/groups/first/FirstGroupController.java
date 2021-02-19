package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceFirstGroupService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;

import static java.util.Collections.singletonList;

@EqualsAndHashCode(callSuper = true)
@Data
public class FirstGroupController extends AbstractController implements UnheatedSurfaceReportContainer {

    @FXML
    private VBox firstGroupParamsVbox;

    @FXML
    private FirstGroupParamsController firstGroupParamsController;

    @FXML
    private FunctionParamsController functionParamsController;

    @FXML
    private FirstGroupChartController firstGroupChartController;

    private UnheatedSurfaceController unheatedSurfaceController;

    @Inject
    private UnheatedSurfaceFirstGroupService unheatedSurfaceFirstGroupService;

    @Override
    protected void initialize() {
        firstGroupParamsController.setFirstGroupController(this);
        firstGroupChartController.setFirstGroupController(this);

        functionParamsController.setParentController(this);
        functionParamsController.setInterpolationService(unheatedSurfaceFirstGroupService);

        functionParamsController.setPropertiesMapper(props ->
                props.getReportPropertiesByClass(UnheatedSurfaceProperties.class).orElseThrow().getFirstGroup());

        functionParamsController.setNodesToBlockOnUpdate(singletonList(firstGroupParamsVbox));

        functionParamsController.setInterpolationPointConstructor((time, value) -> new IntegerPoint(time, value.intValue()));
    }

    @Override
    public void postConstruct() {
        firstGroupParamsController.postConstruct();
        functionParamsController.postConstruct();
    }

    @Override
    public Sample getSample() {
        return unheatedSurfaceController.getSample();
    }

    @Override
    public UnheatedSurfaceReport getReport() {
        return unheatedSurfaceController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return firstGroupChartController;
    }
}
