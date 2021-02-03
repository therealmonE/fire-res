package io.github.therealmone.fireres.gui.controller.heat.flow;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
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
public class HeatFlowParamsController extends AbstractController implements SampleContainer {

    @ParentController
    private HeatFlowPaneController heatFlowPaneController;

    @FXML
    private Spinner<Double> sensorSpinner;

    @FXML
    private Spinner<Double> heatFlowSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private GenerationProperties generationProperties;

    @Override
    protected void initialize() {
        sensorSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSpinnerFocusChanged(newValue, sensorSpinner));

        heatFlowSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSpinnerFocusChanged(newValue, heatFlowSpinner));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetHeatFlowParameters(this);
    }

    private void handleSpinnerFocusChanged(Boolean newValue, Spinner<?> spinner) {
        if (!newValue) {
            log.info("Spinner {} lost focus, sample id: {}", spinner.getId(), getSample().getId());
            commitSpinner(spinner);
        }
    }

    @Override
    public Sample getSample() {
        return heatFlowPaneController.getSample();
    }
}
