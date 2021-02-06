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
    private Spinner<Integer> secondThermocoupleGroupNumberOfThermocouplesSpinner;

    @FXML
    private Spinner<Integer> secondThermocoupleGroupBoundSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private UnheatedSurfaceSecondGroupService unheatedSurfaceSecondGroupService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    protected void initialize() {
        secondThermocoupleGroupNumberOfThermocouplesSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSecondThermocoupleGroupNumberOfThermocouplesSpinnerFocusChanged(newValue));

        secondThermocoupleGroupBoundSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSecondThermocoupleGroupBoundSpinnerFocusChanged(newValue));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetSecondThermocoupleGroupParameters(this);
    }

    private void handleSecondThermocoupleGroupNumberOfThermocouplesSpinnerFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, secondThermocoupleGroupNumberOfThermocouplesSpinner, () -> {
            unheatedSurfaceSecondGroupService.updateThermocoupleCount((UnheatedSurfaceReport) getReport(), secondThermocoupleGroupNumberOfThermocouplesSpinner.getValue());
            chartsSynchronizationService.syncSecondThermocoupleGroupChart(
                    secondThermocoupleGroupPaneController.getSecondThermocoupleGroupChartController().getSecondThermocoupleGroupChart(), (UnheatedSurfaceReport) getReport());
        });
    }

    private void handleSecondThermocoupleGroupBoundSpinnerFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, secondThermocoupleGroupBoundSpinner, () -> {
            unheatedSurfaceSecondGroupService.updateBound((UnheatedSurfaceReport) getReport(), secondThermocoupleGroupBoundSpinner.getValue());
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
