package io.github.therealmone.fireres.gui.controller.fire.mode;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FireModePaneController extends AbstractController implements SampleContainer {
    @ParentController
    private SampleTabController sampleTabController;

    @FXML
    @ChildController
    private FireModeParamsController fireModeParamsController;

    @FXML
    @ChildController
    private FunctionParamsController functionParamsController;

    @Override
    public SampleProperties getSampleProperties() {
        return sampleTabController.getSampleProperties();
    }

    @Override
    protected void initialize() {
        fireModeParamsController.setFireModePaneController(this);
        functionParamsController.setParentController(this);
    }

    @Override
    public void postConstruct() {
        fireModeParamsController.postConstruct();
        functionParamsController.postConstruct();
    }
}
