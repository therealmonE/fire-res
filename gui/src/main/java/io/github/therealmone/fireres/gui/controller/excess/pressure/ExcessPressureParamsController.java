package io.github.therealmone.fireres.gui.controller.excess.pressure;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class ExcessPressureParamsController extends AbstractController implements ReportContainer {

    @ParentController
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

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

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
        handleSpinnerLostFocus(focusValue, basePressureSpinner, () -> {
            excessPressureService.updateBasePressure((ExcessPressureReport) getReport(), basePressureSpinner.getValue());
            chartsSynchronizationService.syncExcessPressureChart(
                    excessPressureController.getExcessPressureChartController().getExcessPressureChart(),
                    (ExcessPressureReport) getReport());
        });
    }
    private void handleDispersionCoefficientLostFocus(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, dispersionCoefficientSpinner, () -> {
            excessPressureService.updateDispersionCoefficient((ExcessPressureReport) getReport(), dispersionCoefficientSpinner.getValue());
            chartsSynchronizationService.syncExcessPressureChart(
                    excessPressureController.getExcessPressureChartController().getExcessPressureChart(),
                    (ExcessPressureReport) getReport());
        });
    }

    private void handleDeltaSpinnerLostFocus(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, deltaSpinner, () -> {
            excessPressureService.updateDelta((ExcessPressureReport) getReport(), deltaSpinner.getValue());
            chartsSynchronizationService.syncExcessPressureChart(
                    excessPressureController.getExcessPressureChartController().getExcessPressureChart(),
                    (ExcessPressureReport) getReport());
        });
    }

    @Override
    public Sample getSample() {
        return excessPressureController.getSample();
    }

    @Override
    public Report getReport() {
        return excessPressureController.getReport();
    }
}
