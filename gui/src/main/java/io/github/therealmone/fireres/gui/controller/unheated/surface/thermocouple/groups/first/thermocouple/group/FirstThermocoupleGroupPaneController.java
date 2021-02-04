package io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.first.thermocouple.group;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfacePaneController;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceFirstGroupService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FirstThermocoupleGroupPaneController extends AbstractController implements ReportContainer {

    @FXML
    @ChildController
    private FirstThermocoupleGroupParamsController firstThermocoupleGroupParamsController;

    @FXML
    @ChildController
    private FunctionParamsController functionParamsController;

    @ParentController
    private UnheatedSurfacePaneController unheatedSurfacePaneController;

    @Inject
    private UnheatedSurfaceFirstGroupService unheatedSurfaceFirstGroupService;

    @Override
    public Sample getSample() {
        return unheatedSurfacePaneController.getSample();
    }

    @Override
    protected void initialize() {
        firstThermocoupleGroupParamsController.setFirstThermocoupleGroupPaneController(this);

        functionParamsController.setParentController(this);
        functionParamsController.setInterpolationService(unheatedSurfaceFirstGroupService);
        functionParamsController.setPropertiesMapper(sampleProperties -> sampleProperties.getUnheatedSurface().getFirstGroup());
    }

    @Override
    public void postConstruct() {
        firstThermocoupleGroupParamsController.postConstruct();
        functionParamsController.postConstruct();
    }

    @Override
    public Report getReport() {
        return null;
    }
}
