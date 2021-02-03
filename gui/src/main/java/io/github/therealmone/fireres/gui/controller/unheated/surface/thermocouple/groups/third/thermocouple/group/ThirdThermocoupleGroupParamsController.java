package io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.third.thermocouple.group;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class ThirdThermocoupleGroupParamsController extends AbstractController implements SampleContainer {

    @ParentController
    private ThirdThermocoupleGroupPaneController thirdThermocoupleGroupPaneController;

    @FXML
    private Spinner<Integer> thirdThermocoupleGroupNumberOfThermocouplesSpinner;

    @FXML
    private Spinner<Integer> thirdThermocoupleGroupBoundSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Override
    public Sample getSample() {
        return thirdThermocoupleGroupPaneController.getSample();
    }

    @Override
    protected void initialize() {
        thirdThermocoupleGroupNumberOfThermocouplesSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSpinnerFocusChanged(newValue, thirdThermocoupleGroupNumberOfThermocouplesSpinner));

        thirdThermocoupleGroupBoundSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSpinnerFocusChanged(newValue, thirdThermocoupleGroupBoundSpinner));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetThirdThermocoupleGroupParameters(this);
    }

    private void handleSpinnerFocusChanged(Boolean newValue, Spinner<?> spinner) {
        if (!newValue) {
            log.info("Spinner {} lost focus, sample id: {}", spinner.getId(), getSample().getId());
            commitSpinner(spinner);
        }
    }
}
