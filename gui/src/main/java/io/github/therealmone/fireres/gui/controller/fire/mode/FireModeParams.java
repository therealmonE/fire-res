package io.github.therealmone.fireres.gui.controller.fire.mode;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.Resettable;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.UUID;

@LoadableComponent("/component/fire-mode/fireModeParams.fxml")
public class FireModeParams extends AbstractReportUpdaterComponent<TitledPane>
        implements FireModeReportContainer, Resettable {

    @FXML
    @Getter
    private Spinner<Integer> thermocouples;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private FireModeService fireModeService;

    @SneakyThrows
    private void handleThermocoupleSpinnerFocusChanged(Boolean focusValue) {
        Runnable action = () ->
                fireModeService.updateThermocoupleCount(getReport(), thermocouples.getValue());

        handleSpinnerLostFocus(focusValue, thermocouples, () ->
                updateReport(action, ((FireMode) getParent()).getParamsVbox()));
    }

    @Override
    public Sample getSample() {
        return ((FireMode) getParent()).getSample();
    }

    @Override
    protected void initialize() {
        thermocouples.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocoupleSpinnerFocusChanged(newValue));
    }

    @Override
    public void postConstruct() {
        reset();
    }

    @Override
    public void reset() {
        resetSettingsService.resetFireModeParameters(this);
    }

    @Override
    public FireModeReport getReport() {
        return ((FireMode) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return ((FireMode) getParent()).getChartContainer();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getId();
    }
}
