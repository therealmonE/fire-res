package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
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
public class ThirdGroupParamsController extends AbstractController implements UnheatedSurfaceReportContainer {

    @ParentController
    private ThirdGroupController thirdGroupController;

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
            unheatedSurfaceThirdGroupService.updateThermocoupleCount(getReport(), thirdGroupThermocouplesCountSpinner.getValue());
            chartsSynchronizationService.syncThirdThermocoupleGroupChart(
                    thirdGroupController.getThirdGroupChartController().getThirdGroupChart(), getReport());
        });
    }

    private void handleThermocouplesBoundSpinnerFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, thirdGroupBoundSpinner, () -> {
            unheatedSurfaceThirdGroupService.updateBound(getReport(), thirdGroupBoundSpinner.getValue());
            chartsSynchronizationService.syncThirdThermocoupleGroupChart(
                    thirdGroupController.getThirdGroupChartController().getThirdGroupChart(), getReport());
        });
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
}
