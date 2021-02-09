package io.github.therealmone.fireres.gui.controller.heat.flow;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class HeatFlowParamsController extends AbstractController implements ReportContainer {

    @ParentController
    private HeatFlowController heatFlowController;

    @FXML
    private Spinner<Integer> sensorSpinner;

    @FXML
    private Spinner<Integer> heatFlowBoundSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private HeatFlowService heatFlowService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

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
        handleSpinnerLostFocus(focusValue, sensorSpinner, () -> {
            heatFlowService.updateSensorsCount((HeatFlowReport) getReport(), sensorSpinner.getValue());
            chartsSynchronizationService.syncHeatFlowChart(
                    heatFlowController.getHeatFlowChartController().getHeatFlowChart(),
                    (HeatFlowReport) getReport());
        });
    }
    private void handleHeatFlowBoundSpinnerLostFocus(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, heatFlowBoundSpinner, () -> {
            heatFlowService.updateBound((HeatFlowReport) getReport(), heatFlowBoundSpinner.getValue());
            chartsSynchronizationService.syncHeatFlowChart(
                    heatFlowController.getHeatFlowChartController().getHeatFlowChart(),
                    (HeatFlowReport) getReport());
        });
    }


    @Override
    public Sample getSample() {
        return heatFlowController.getSample();
    }

    @Override
    public Report getReport() {
        return heatFlowController.getReport();
    }
}
