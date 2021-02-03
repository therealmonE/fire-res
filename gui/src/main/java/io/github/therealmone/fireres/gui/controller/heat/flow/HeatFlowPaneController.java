package io.github.therealmone.fireres.gui.controller.heat.flow;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data

public class HeatFlowPaneController extends AbstractController implements ReportContainer {

    @FXML
    @ChildController
    private HeatFlowParamsController heatFlowParamsController;

    @FXML
    @ChildController
    private FunctionParamsController functionParamsController;

    @ParentController
    private SampleTabController sampleTabController;

    @Override
    public Sample getSample() {
        return sampleTabController.getSample();
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

    @Override
    public Report getReport() {
        return null;
    }
}
