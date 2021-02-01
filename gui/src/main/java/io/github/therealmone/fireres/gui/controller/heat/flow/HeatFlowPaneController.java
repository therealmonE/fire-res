package io.github.therealmone.fireres.gui.controller.heat.flow;

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

public class HeatFlowPaneController extends AbstractController implements SampleContainer {

    @FXML
    @ChildController
    private HeatFlowParamsController heatFlowParamsController;

    @FXML
    @ChildController
    private FunctionParamsController functionParamsController;

    @ParentController
    private SampleTabController sampleTabController;

    @Override
    public SampleProperties getSampleProperties() {
        return sampleTabController.getSampleProperties();
    }

    @Override
    protected void initialize() {
        heatFlowParamsController.setHeatFlowPaneController(this);
        functionParamsController.setParentController(this);
    }

    @Override
    public void postConstruct() {
        heatFlowParamsController.postConstruct();
        functionParamsController.postConstruct();
    }
}
