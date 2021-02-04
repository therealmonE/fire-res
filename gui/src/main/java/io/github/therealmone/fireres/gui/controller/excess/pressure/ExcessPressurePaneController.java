package io.github.therealmone.fireres.gui.controller.excess.pressure;

import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExcessPressurePaneController extends AbstractController implements SampleContainer {

    @FXML
    @ChildController
    private ExcessPressureParamsController excessPressureParamsController;

    @ParentController
    private SampleTabController sampleTabController;

    @Override
    public Sample getSample() {
        return sampleTabController.getSample();
    }

    @Override
    protected void initialize() {
        excessPressureParamsController.setExcessPressurePaneController(this);
    }

    @Override
    public void postConstruct() {
        excessPressureParamsController.postConstruct();
    }
}
