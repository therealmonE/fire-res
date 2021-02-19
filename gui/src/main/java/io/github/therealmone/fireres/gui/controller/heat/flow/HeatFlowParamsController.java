package io.github.therealmone.fireres.gui.controller.heat.flow;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class HeatFlowParamsController extends AbstractReportUpdaterController implements HeatFlowReportContainer {

    private HeatFlowController heatFlowController;

    @FXML
    private Spinner<Integer> sensorSpinner;

    @FXML
    private Spinner<Double> heatFlowBoundSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private HeatFlowService heatFlowService;

    @Override
    protected void initialize() {
        sensorSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSensorSpinnerLostFocus(newValue));

        heatFlowBoundSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleHeatFlowBoundSpinnerLostFocus(newValue));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetHeatFlowParameters(this);
    }

    private void handleSensorSpinnerLostFocus(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, sensorSpinner, () ->
                updateReport(() -> heatFlowService.updateSensorsCount(getReport(), sensorSpinner.getValue())));
    }
    private void handleHeatFlowBoundSpinnerLostFocus(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, heatFlowBoundSpinner, () ->
                updateReport(() -> heatFlowService.updateBound(getReport(), heatFlowBoundSpinner.getValue())));
    }

    @Override
    public Sample getSample() {
        return heatFlowController.getSample();
    }

    @Override
    public HeatFlowReport getReport() {
        return heatFlowController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return heatFlowController.getChartContainer();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getId();
    }
}
