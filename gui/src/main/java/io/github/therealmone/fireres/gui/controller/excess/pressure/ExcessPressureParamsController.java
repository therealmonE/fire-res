package io.github.therealmone.fireres.gui.controller.excess.pressure;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class ExcessPressureParamsController extends AbstractReportUpdaterController implements ExcessPressureReportContainer {

    private ExcessPressureController excessPressureController;

    @FXML
    private Spinner<Double> basePressureSpinner;

    @FXML
    private Spinner<Double> dispersionCoefficientSpinner;

    @FXML
    private Spinner<Double> deltaSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private ExcessPressureService excessPressureService;

    @Override
    protected void initialize() {
        basePressureSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleBasePressureSpinnerLostFocus(newValue));

        dispersionCoefficientSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleDispersionCoefficientLostFocus(newValue));

        deltaSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleDeltaSpinnerLostFocus(newValue));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetExcessPressureParameters(this);
    }

    private void handleBasePressureSpinnerLostFocus(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, basePressureSpinner, () ->
                updateReport(() -> excessPressureService.updateBasePressure(getReport(), basePressureSpinner.getValue())));
    }
    private void handleDispersionCoefficientLostFocus(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, dispersionCoefficientSpinner, () ->
                updateReport(() -> excessPressureService.updateDispersionCoefficient(getReport(), dispersionCoefficientSpinner.getValue())));
    }

    private void handleDeltaSpinnerLostFocus(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, deltaSpinner, () ->
                updateReport(() -> excessPressureService.updateDelta(getReport(), deltaSpinner.getValue())));
    }

    @Override
    public Sample getSample() {
        return excessPressureController.getSample();
    }

    @Override
    public ExcessPressureReport getReport() {
        return excessPressureController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return excessPressureController.getChartContainer();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getId();
    }
}
