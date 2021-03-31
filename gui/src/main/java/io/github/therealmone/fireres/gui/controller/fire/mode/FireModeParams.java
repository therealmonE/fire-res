package io.github.therealmone.fireres.gui.controller.fire.mode;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.model.FireModeType;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportUpdater;
import io.github.therealmone.fireres.gui.controller.Resettable;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;

import java.util.UUID;

@LoadableComponent("/component/fire-mode/fireModeParams.fxml")
public class FireModeParams extends AbstractReportUpdaterComponent<TitledPane>
        implements FireModeReportContainer, Resettable {

    @FXML
    @Getter
    private CheckBox showMeanTemperature;

    @FXML
    @Getter
    private CheckBox showBounds;

    @FXML
    @Getter
    private Spinner<Integer> temperatureMaintaining;

    @FXML
    @Getter
    private ChoiceBox<FireModeType> fireModeType;

    @FXML
    @Getter
    private Spinner<Integer> thermocouples;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private FireModeService fireModeService;

    @Override
    protected void initialize() {
        thermocouples.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocoupleSpinnerFocusChanged(newValue));

        temperatureMaintaining.focusedProperty().addListener((observableValue, oldValue, newValue) ->
                handleTemperatureMaintainingSpinnerFocusChanged(newValue));

        showBounds.setOnAction(event -> handleShowBoundsChanged());

        showMeanTemperature.setOnAction(event -> handleShowMeanTemperatureChanged());
    }

    @Override
    public void postConstruct() {
        reset();

        fireModeType.getItems().addAll(FireModeType.values());

        fireModeType.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
                handleFireModeTypeChanged());
    }

    @Override
    public void reset() {
        resetSettingsService.resetFireModeParameters(this);
    }

    @SneakyThrows
    private void handleThermocoupleSpinnerFocusChanged(Boolean focusValue) {
        Runnable action = () ->
                fireModeService.updateThermocoupleCount(getReport(), thermocouples.getValue());

        handleSpinnerLostFocus(focusValue, thermocouples, () ->
                updateReport(action, ((FireMode) getParent()).getParamsVbox()));
    }

    @SneakyThrows
    private void handleTemperatureMaintainingSpinnerFocusChanged(Boolean focusValue) {
        Runnable action = () -> {
            val value = temperatureMaintaining.getValue();

            fireModeService.updateTemperatureMaintaining(getReport(), value == 0 ? null : value);
        };

        handleSpinnerLostFocus(focusValue, temperatureMaintaining, () ->
                updateReport(action, ((FireMode) getParent()).getParamsVbox()));
    }

    private void handleFireModeTypeChanged() {
        updateReport(() -> fireModeService.updateFireModeType(
                getReport(), fireModeType.getValue()), ((FireMode) getParent()).getParamsVbox());
    }

    private void handleShowBoundsChanged() {
        updateReport(() -> fireModeService.updateShowBounds(
                getReport(), showBounds.isSelected()), ((FireMode) getParent()).getParamsVbox());
    }

    private void handleShowMeanTemperatureChanged() {
        updateReport(() -> fireModeService.updateShowMeanTemperature(
                getReport(), showMeanTemperature.isSelected()), ((FireMode) getParent()).getParamsVbox());
    }

    @Override
    public Sample getSample() {
        return ((FireMode) getParent()).getSample();
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
    public UUID getUpdatingElementId() {
        return ((ReportUpdater) getParent()).getUpdatingElementId();
    }
}
