package io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.third.thermocouple.group;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceThirdGroupService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class ThirdThermocoupleGroupParamsController extends AbstractController implements ReportContainer {

    @ParentController
    private ThirdThermocoupleGroupPaneController thirdThermocoupleGroupPaneController;

    @FXML
    private Spinner<Integer> thirdThermocoupleGroupNumberOfThermocouplesSpinner;

    @FXML
    private Spinner<Integer> thirdThermocoupleGroupBoundSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private UnheatedSurfaceThirdGroupService unheatedSurfaceThirdGroupService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    protected void initialize() {
        thirdThermocoupleGroupNumberOfThermocouplesSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThirdThermocoupleGroupNumberOfThermocouplesSpinnerFocusChanged(newValue));

        thirdThermocoupleGroupBoundSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThirdThermocoupleGroupBoundSpinnerFocusChanged(newValue));
    }

    private void handleThirdThermocoupleGroupNumberOfThermocouplesSpinnerFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, thirdThermocoupleGroupNumberOfThermocouplesSpinner, () -> {
            unheatedSurfaceThirdGroupService.updateThermocoupleCount((UnheatedSurfaceReport) getReport(), thirdThermocoupleGroupNumberOfThermocouplesSpinner.getValue());
            chartsSynchronizationService.syncThirdThermocoupleGroupChart(
                    thirdThermocoupleGroupPaneController.getThirdThermocoupleGroupChartController().getThirdThermocoupleGroupChart(), (UnheatedSurfaceReport) getReport());
        });
    }

    private void handleThirdThermocoupleGroupBoundSpinnerFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, thirdThermocoupleGroupBoundSpinner, () -> {
            unheatedSurfaceThirdGroupService.updateBound((UnheatedSurfaceReport) getReport(), thirdThermocoupleGroupBoundSpinner.getValue());
            chartsSynchronizationService.syncThirdThermocoupleGroupChart(
                    thirdThermocoupleGroupPaneController.getThirdThermocoupleGroupChartController().getThirdThermocoupleGroupChart(), (UnheatedSurfaceReport) getReport());
        });
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetThirdThermocoupleGroupParameters(this);
    }


    @Override
    public Sample getSample() {
        return thirdThermocoupleGroupPaneController.getSample();
    }

    @Override
    public Report getReport() {
        return thirdThermocoupleGroupPaneController.getReport();
    }
}
