package io.github.therealmone.fireres.gui.controller.fire.mode;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
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
public class FireModeParamsController extends AbstractReportUpdaterController implements FireModeReportContainer {

    private FireModeController fireModeController;

    @FXML
    private Spinner<Integer> thermocoupleSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FireModeService fireModeService;

    @Inject
    private ReportExecutorService reportExecutorService;

    @SneakyThrows
    private void handleThermocoupleSpinnerFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, thermocoupleSpinner, () ->
                updateReport(() -> fireModeService.updateThermocoupleCount(getReport(), thermocoupleSpinner.getValue())));
    }

    @Override
    public Sample getSample() {
        return fireModeController.getSample();
    }

    @Override
    protected void initialize() {
        thermocoupleSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocoupleSpinnerFocusChanged(newValue));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetFireModeParameters(this);
    }

    @Override
    public FireModeReport getReport() {
        return fireModeController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return getFireModeController().getFireModeChartController();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getId();
    }
}
