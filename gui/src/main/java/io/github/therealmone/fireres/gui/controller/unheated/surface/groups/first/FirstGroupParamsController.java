package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceFirstGroupService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class FirstGroupParamsController extends AbstractReportUpdaterController implements UnheatedSurfaceReportContainer {

    private FirstGroupController firstGroupController;

    @FXML
    private Spinner<Integer> firstGroupThermocouplesCountSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private UnheatedSurfaceFirstGroupService unheatedSurfaceFirstGroupService;

    @SneakyThrows
    private void handleThermocouplesCountSpinnerFocusChanged(Boolean focusValue) {
        Runnable action = () ->
                unheatedSurfaceFirstGroupService.updateThermocoupleCount(
                        getReport(),
                        firstGroupThermocouplesCountSpinner.getValue());

        handleSpinnerLostFocus(focusValue, firstGroupThermocouplesCountSpinner, () ->
                updateReport(action, firstGroupController.getFirstGroupParamsVbox()));
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
        return firstGroupController.getSample();
    }

    @Override
    public UnheatedSurfaceReport getReport() {
        return firstGroupController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return firstGroupController.getChartContainer();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getFirstGroup().getId();
    }

}
