package io.github.therealmone.fireres.gui.controller.unheated.surface;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.first.thermocouple.group.FirstThermocoupleGroupPaneController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.second.thermocouple.group.SecondThermocoupleGroupPaneController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.third.thermocouple.group.ThirdThermocoupleGroupPaneController;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnheatedSurfacePaneController extends AbstractController implements SampleContainer {

    @FXML
    @ChildController
    private FirstThermocoupleGroupPaneController firstThermocoupleGroupPaneController;

    @FXML
    @ChildController
    private SecondThermocoupleGroupPaneController secondThermocoupleGroupPaneController;

    @FXML
    @ChildController
    private ThirdThermocoupleGroupPaneController thirdThermocoupleGroupPaneController;

    @ParentController
    private SampleTabController sampleTabController;

    @Override
    public Sample getSample() {
        return sampleTabController.getSample();
    }

    @Override
    protected void initialize() {
        firstThermocoupleGroupPaneController.setUnheatedSurfacePaneController(this);
        secondThermocoupleGroupPaneController.setUnheatedSurfacePaneController(this);
        thirdThermocoupleGroupPaneController.setUnheatedSurfacePaneController(this);
    }

    @Override
    public void postConstruct() {
        firstThermocoupleGroupPaneController.postConstruct();
        secondThermocoupleGroupPaneController.postConstruct();
        thirdThermocoupleGroupPaneController.postConstruct();
    }
}
