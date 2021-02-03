package io.github.therealmone.fireres.gui.controller.fire.mode;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class FireModeParamsController extends AbstractController implements ReportContainer {

    @ParentController
    private FireModePaneController fireModePaneController;

    @FXML
    private Spinner<Integer> thermocoupleSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FireModeService fireModeService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @SneakyThrows
    private void handleThermocoupleSpinnerFocusChanged(Boolean newValue) {
        if (!newValue) {
            log.info("Spinner {} lost focus, sample id: {}", thermocoupleSpinner.getId(), getSample().getId());
            commitSpinner(thermocoupleSpinner);

            fireModeService.updateThermocoupleCount((FireModeReport) getReport(), thermocoupleSpinner.getValue());
            log.info("Report updated");

            chartsSynchronizationService.syncFireModeChart(
                    fireModePaneController.getFireModeChartController().getFireModeChart(), (FireModeReport) getReport());
        }
    }

    @Override
    public Sample getSample() {
        return fireModePaneController.getSample();
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
    public Report getReport() {
        return fireModePaneController.getReport();
    }
}
