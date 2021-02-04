package io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.third.thermocouple.group;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfacePaneController;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceThirdGroupService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data

public class ThirdThermocoupleGroupPaneController extends AbstractController implements ReportContainer {

    @FXML
    @ChildController
    private ThirdThermocoupleGroupParamsController thirdThermocoupleGroupParamsController;

    @FXML
    @ChildController
    private FunctionParamsController functionParamsController;

    @ParentController
    private UnheatedSurfacePaneController unheatedSurfacePaneController;

    @Inject
    private UnheatedSurfaceThirdGroupService unheatedSurfaceThirdGroupService;

    @Override
    public Sample getSample() {
        return unheatedSurfacePaneController.getSample();
    }

    @Override
    protected void initialize() {
        thirdThermocoupleGroupParamsController.setThirdThermocoupleGroupPaneController(this);

        functionParamsController.setParentController(this);
        functionParamsController.setInterpolationService(unheatedSurfaceThirdGroupService);
        functionParamsController.setPropertiesMapper(sampleProperties -> sampleProperties.getUnheatedSurface().getThirdGroup());
    }

    @Override
    public void postConstruct() {
        thirdThermocoupleGroupParamsController.postConstruct();
        functionParamsController.postConstruct();
    }

    @Override
    public Report getReport() {
        return null;
    }
}
