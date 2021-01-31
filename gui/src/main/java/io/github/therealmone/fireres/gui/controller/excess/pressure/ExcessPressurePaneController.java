package io.github.therealmone.fireres.gui.controller.excess.pressure;

import io.github.therealmone.fireres.core.config.SampleProperties;
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
    private ExcessPressureParamsController excessPressureParamsController;

    private SampleTabController sampleTabController;

    @Override
    public SampleProperties getSampleProperties() {
        return sampleTabController.getSampleProperties();
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
