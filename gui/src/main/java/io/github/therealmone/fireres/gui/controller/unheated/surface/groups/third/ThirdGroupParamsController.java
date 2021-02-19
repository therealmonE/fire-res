package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceThirdGroupService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class ThirdGroupParamsController extends AbstractReportUpdaterController implements UnheatedSurfaceReportContainer {

    private ThirdGroupController thirdGroupController;

    @FXML
    private Spinner<Integer> thirdGroupThermocouplesCountSpinner;

    @FXML
    private Spinner<Integer> thirdGroupBoundSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private UnheatedSurfaceThirdGroupService unheatedSurfaceThirdGroupService;

    @Override
    protected void initialize() {
        thirdGroupThermocouplesCountSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocouplesCountSpinnerFocusChanged(newValue));

        thirdGroupBoundSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocouplesBoundSpinnerFocusChanged(newValue));
    }

    private void handleThermocouplesCountSpinnerFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, thirdGroupThermocouplesCountSpinner, () ->
                updateReport(() -> unheatedSurfaceThirdGroupService.updateThermocoupleCount(
                        getReport(),
                        thirdGroupThermocouplesCountSpinner.getValue())));
    }

    private void handleThermocouplesBoundSpinnerFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, thirdGroupBoundSpinner, () ->
                updateReport(() -> unheatedSurfaceThirdGroupService.updateBound(
                        getReport(),
                        thirdGroupBoundSpinner.getValue())));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetThirdThermocoupleGroupParameters(this);
    }

    @Override
    public Sample getSample() {
        return thirdGroupController.getSample();
    }

    @Override
    public UnheatedSurfaceReport getReport() {
        return thirdGroupController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return thirdGroupController.getChartContainer();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getId();
    }
}
