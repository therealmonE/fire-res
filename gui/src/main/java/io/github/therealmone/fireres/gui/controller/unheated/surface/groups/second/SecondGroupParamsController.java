package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.second;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceSecondGroupService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class SecondGroupParamsController extends AbstractReportUpdaterController implements UnheatedSurfaceReportContainer {

    private SecondGroupController secondGroupController;

    @FXML
    private Spinner<Integer> secondGroupThermocouplesCountSpinner;

    @FXML
    private Spinner<Integer> secondGroupBoundSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private UnheatedSurfaceSecondGroupService unheatedSurfaceSecondGroupService;

    @Override
    protected void initialize() {
        secondGroupThermocouplesCountSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocouplesCountSpinnerFocusChanged(newValue));

        secondGroupBoundSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocouplesBoundSpinnerFocusChanged(newValue));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetSecondThermocoupleGroupParameters(this);
    }

    private void handleThermocouplesCountSpinnerFocusChanged(Boolean focusValue) {
        Runnable action = () ->
                unheatedSurfaceSecondGroupService.updateThermocoupleCount(
                        getReport(),
                        secondGroupThermocouplesCountSpinner.getValue());

        handleSpinnerLostFocus(focusValue, secondGroupThermocouplesCountSpinner, () ->
                updateReport(action, secondGroupController.getSecondGroupParamsVbox()));
    }

    private void handleThermocouplesBoundSpinnerFocusChanged(Boolean focusValue) {
        Runnable action = () ->
                unheatedSurfaceSecondGroupService.updateBound(
                        getReport(),
                        secondGroupBoundSpinner.getValue());

        handleSpinnerLostFocus(focusValue, secondGroupBoundSpinner, () ->
                updateReport(action, secondGroupController.getSecondGroupParamsVbox()));
    }

    @Override
    public Sample getSample() {
        return secondGroupController.getSample();
    }

    @Override
    public UnheatedSurfaceReport getReport() {
        return secondGroupController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return secondGroupController.getChartContainer();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getSecondGroup().getId();
    }

}
