package io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.second.thermocouple.group;

import com.google.inject.Inject;
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
public class SecondThermocoupleGroupParamsController extends AbstractController implements SampleContainer {

    @ParentController
    private SecondThermocoupleGroupPaneController secondThermocoupleGroupPaneController;

    @FXML
    private Spinner<Integer> secondThermocoupleGroupNumberOfThermocouplesSpinner;

    @FXML
    private Spinner<Integer> secondThermocoupleGroupBoundSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Override
    protected void initialize() {
        secondThermocoupleGroupNumberOfThermocouplesSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSpinnerFocusChanged(newValue, secondThermocoupleGroupNumberOfThermocouplesSpinner));

        secondThermocoupleGroupBoundSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSpinnerFocusChanged(newValue, secondThermocoupleGroupBoundSpinner));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetSecondThermocoupleGroupParameters(this);
    }

    private void handleSpinnerFocusChanged(Boolean newValue, Spinner<?> spinner) {
        if (!newValue) {
            log.info("Spinner {} lost focus, sample id: {}", spinner.getId(), getSample().getId());
            commitSpinner(spinner);
        }
    }

    @Override
    public Sample getSample() {
        return secondThermocoupleGroupPaneController.getSample();
    }

}
