package io.github.therealmone.fireres.gui.controller.heat.flow;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import lombok.Getter;

import java.util.UUID;

@LoadableComponent("/component/heat-flow/heatFlowParams.fxml")
public class HeatFlowParams extends AbstractReportUpdaterComponent<TitledPane>
        implements HeatFlowReportContainer {

    @FXML
    @Getter
    private Spinner<Integer> sensors;

    @FXML
    @Getter
    private Spinner<Double> bound;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private HeatFlowService heatFlowService;

    @Override
    protected void initialize() {
        sensors.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSensorSpinnerLostFocus(newValue));

        bound.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleHeatFlowBoundSpinnerLostFocus(newValue));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetHeatFlowParameters(this);
    }

    private void handleSensorSpinnerLostFocus(Boolean focusValue) {
        Runnable action = () ->
                heatFlowService.updateSensorsCount(getReport(), sensors.getValue());

        handleSpinnerLostFocus(focusValue, sensors, () ->
                updateReport(action, ((HeatFlow) getParent()).getParamsVbox()));
    }

    private void handleHeatFlowBoundSpinnerLostFocus(Boolean focusValue) {
        Runnable action = () ->
                heatFlowService.updateBound(getReport(), bound.getValue());

        handleSpinnerLostFocus(focusValue, bound, () ->
                updateReport(action, ((HeatFlow) getParent()).getParamsVbox()));
    }

    @Override
    public Sample getSample() {
        return ((HeatFlow) getParent()).getSample();
    }

    @Override
    public HeatFlowReport getReport() {
        return ((HeatFlow) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return ((HeatFlow) getParent()).getChartContainer();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getId();
    }
}
