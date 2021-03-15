package io.github.therealmone.fireres.gui.controller.excess.pressure;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportUpdater;
import io.github.therealmone.fireres.gui.controller.Resettable;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import lombok.Getter;

import java.util.UUID;

@LoadableComponent("/component/excess-pressure/excessPressureParams.fxml")
public class ExcessPressureParams extends AbstractReportUpdaterComponent<TitledPane>
        implements ExcessPressureReportContainer, Resettable {

    @FXML
    @Getter
    private Spinner<Double> basePressure;

    @FXML
    @Getter
    private Spinner<Double> dispersionCoefficient;

    @FXML
    @Getter
    private Spinner<Double> delta;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private ExcessPressureService excessPressureService;

    @Override
    protected void initialize() {
        basePressure.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleBasePressureSpinnerLostFocus(newValue));

        dispersionCoefficient.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleDispersionCoefficientLostFocus(newValue));

        delta.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleDeltaSpinnerLostFocus(newValue));
    }

    @Override
    public void postConstruct() {
        reset();
    }

    @Override
    public void reset() {
        resetSettingsService.resetExcessPressureParameters(this);
    }

    private void handleBasePressureSpinnerLostFocus(Boolean focusValue) {
        Runnable action = () ->
                excessPressureService.updateBasePressure(getReport(), basePressure.getValue());

        handleSpinnerLostFocus(focusValue, basePressure, () ->
                updateReport(action, ((ExcessPressure) getParent()).getParamsVbox()));
    }

    private void handleDispersionCoefficientLostFocus(Boolean focusValue) {
        Runnable action = () ->
                excessPressureService.updateDispersionCoefficient(getReport(), dispersionCoefficient.getValue());

        handleSpinnerLostFocus(focusValue, dispersionCoefficient, () ->
                updateReport(action, ((ExcessPressure) getParent()).getParamsVbox()));
    }

    private void handleDeltaSpinnerLostFocus(Boolean focusValue) {
        Runnable action = () ->
                excessPressureService.updateDelta(getReport(), delta.getValue());

        handleSpinnerLostFocus(focusValue, delta, () ->
                updateReport(action, ((ExcessPressure) getParent()).getParamsVbox()));
    }

    @Override
    public Sample getSample() {
        return ((ExcessPressure) getParent()).getSample();
    }

    @Override
    public ExcessPressureReport getReport() {
        return ((ExcessPressure) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return ((ExcessPressure) getParent()).getChartContainer();
    }

    @Override
    public UUID getUpdatingElementId() {
        return ((ReportUpdater) getParent()).getUpdatingElementId();
    }
}
