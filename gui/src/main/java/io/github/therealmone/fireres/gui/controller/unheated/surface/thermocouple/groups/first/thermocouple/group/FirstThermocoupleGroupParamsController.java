package io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.first.thermocouple.group;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.SampleProperties;
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
public class FirstThermocoupleGroupParamsController extends AbstractController implements SampleContainer {

    @ParentController
    private FirstThermocoupleGroupPaneController firstThermocoupleGroupPaneController;

    @FXML
    private Spinner<Integer> firstThermocoupleGroupNumberOfThermocouplesSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Override
    protected void initialize() {
        firstThermocoupleGroupNumberOfThermocouplesSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSpinnerFocusChanged(newValue, firstThermocoupleGroupNumberOfThermocouplesSpinner));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetFirstThermocoupleGroupParameters(this);
    }

    private void handleSpinnerFocusChanged(Boolean newValue, Spinner<?> spinner) {
        if (!newValue) {
            log.info("Spinner {} lost focus, sample id: {}", spinner.getId(), getSampleProperties().getId());
            commitSpinner(spinner);
        }
    }

    @Override
    public SampleProperties getSampleProperties() {
        return firstThermocoupleGroupPaneController.getSampleProperties();
    }

}
