package io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.second.thermocouple.group;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceSecondGroupService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class SecondThermocoupleGroupParamsController extends AbstractController implements ReportContainer {

    @ParentController
    private SecondThermocoupleGroupPaneController secondThermocoupleGroupPaneController;

    @FXML
    private Spinner<Integer> secondGroupThermocouplesCountSpinner;

    @FXML
    private Spinner<Integer> secondGroupBoundSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private UnheatedSurfaceSecondGroupService unheatedSurfaceSecondGroupService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

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
        handleSpinnerLostFocus(focusValue, secondGroupThermocouplesCountSpinner, () -> {
            unheatedSurfaceSecondGroupService.updateThermocoupleCount((UnheatedSurfaceReport) getReport(), secondGroupThermocouplesCountSpinner.getValue());
            chartsSynchronizationService.syncSecondThermocoupleGroupChart(
                    secondThermocoupleGroupPaneController.getSecondThermocoupleGroupChartController().getSecondThermocoupleGroupChart(), (UnheatedSurfaceReport) getReport());
        });
    }

    private void handleThermocouplesBoundSpinnerFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, secondGroupBoundSpinner, () -> {
            unheatedSurfaceSecondGroupService.updateBound((UnheatedSurfaceReport) getReport(), secondGroupBoundSpinner.getValue());
            chartsSynchronizationService.syncSecondThermocoupleGroupChart(
                    secondThermocoupleGroupPaneController.getSecondThermocoupleGroupChartController().getSecondThermocoupleGroupChart(), (UnheatedSurfaceReport) getReport());
        });
    }

    @Override
    public Sample getSample() {
        return secondThermocoupleGroupPaneController.getSample();
    }

    @Override
    public Report getReport() {
        return secondThermocoupleGroupPaneController.getReport();
    }

}
