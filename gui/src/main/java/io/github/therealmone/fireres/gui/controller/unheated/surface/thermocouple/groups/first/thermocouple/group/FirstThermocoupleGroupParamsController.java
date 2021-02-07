package io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.first.thermocouple.group;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceFirstGroupService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class FirstThermocoupleGroupParamsController extends AbstractController implements ReportContainer {

    @ParentController
    private FirstThermocoupleGroupPaneController firstThermocoupleGroupPaneController;

    @FXML
    private Spinner<Integer> firstGroupThermocouplesCountSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private UnheatedSurfaceFirstGroupService unheatedSurfaceFirstGroupService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @SneakyThrows
    private void handleThermocouplesCountSpinnerFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, firstGroupThermocouplesCountSpinner, () -> {
            unheatedSurfaceFirstGroupService.updateThermocoupleCount((UnheatedSurfaceReport) getReport(), firstGroupThermocouplesCountSpinner.getValue());
            chartsSynchronizationService.syncFirstThermocoupleGroupChart(
                    firstThermocoupleGroupPaneController.getFirstThermocoupleGroupChartController().getFirstThermocoupleGroupChart(), (UnheatedSurfaceReport) getReport());
        });
    }

    @Override
    protected void initialize() {
        firstGroupThermocouplesCountSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocouplesCountSpinnerFocusChanged(newValue));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetFirstThermocoupleGroupParameters(this);
    }

    @Override
    public Sample getSample() {
        return firstThermocoupleGroupPaneController.getSample();
    }

    @Override
    public Report getReport() {
        return firstThermocoupleGroupPaneController.getReport();
    }

}
