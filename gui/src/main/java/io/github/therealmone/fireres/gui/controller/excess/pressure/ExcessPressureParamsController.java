package io.github.therealmone.fireres.gui.controller.excess.pressure;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
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
public class ExcessPressureParamsController extends AbstractController implements SampleContainer {

    @ParentController
    private ExcessPressurePaneController excessPressurePaneController;

    @FXML
    private Spinner<Double> basePressureSpinner;

    @FXML
    private Spinner<Double> dispersionCoefficientSpinner;

    @FXML
    private Spinner<Double> deltaSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private GenerationProperties generationProperties;

    @Override
    protected void initialize() {
        basePressureSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSpinnerFocusChanged(newValue, basePressureSpinner));

        dispersionCoefficientSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSpinnerFocusChanged(newValue, dispersionCoefficientSpinner));

        deltaSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSpinnerFocusChanged(newValue, deltaSpinner));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetExcessPressureParameters(this);
    }

    private void handleSpinnerFocusChanged(Boolean newValue, Spinner<?> spinner) {
        if (!newValue) {
            log.info("Spinner {} lost focus, sample id: {}", spinner.getId(), getSampleProperties().getId());
            commitSpinner(spinner);
        }
    }

    @Override
    public SampleProperties getSampleProperties() {
        return excessPressurePaneController.getSampleProperties();
    }
}
