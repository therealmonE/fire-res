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
    private Spinner<Integer> thirdGroupThermocouplesCountSpinner;

    @FXML
    private Spinner<Integer> thirdGroupBoundSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private UnheatedSurfaceThirdGroupService unheatedSurfaceThirdGroupService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Override
    protected void initialize() {
        thirdGroupThermocouplesCountSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocouplesCountSpinnerFocusChanged(newValue));

        thirdGroupBoundSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocouplesBoundSpinnerFocusChanged(newValue));
    }

    private void handleThermocouplesCountSpinnerFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, thirdGroupThermocouplesCountSpinner, () -> {
            unheatedSurfaceThirdGroupService.updateThermocoupleCount((UnheatedSurfaceReport) getReport(), thirdGroupThermocouplesCountSpinner.getValue());
            chartsSynchronizationService.syncThirdThermocoupleGroupChart(
                    thirdThermocoupleGroupPaneController.getThirdThermocoupleGroupChartController().getThirdThermocoupleGroupChart(), (UnheatedSurfaceReport) getReport());
        });
    }

    private void handleThermocouplesBoundSpinnerFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, thirdGroupBoundSpinner, () -> {
            unheatedSurfaceThirdGroupService.updateBound((UnheatedSurfaceReport) getReport(), thirdGroupBoundSpinner.getValue());
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
