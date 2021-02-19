package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class GeneralParamsController extends AbstractController {

    @FXML
    private TitledPane generalParamsTitledPane;

    private MainSceneController mainSceneController;

    @FXML
    private Spinner<Integer> timeSpinner;

    @FXML
    private Spinner<Integer> environmentTemperatureSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void postConstruct() {
        resetSettingsService.resetGeneralParameters(this);

        timeSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleTimeSpinnerLostFocus(newValue));

        environmentTemperatureSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleEnvironmentTemperatureSpinnerLostFocus(newValue));
    }

    private void handleTimeSpinnerLostFocus(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, timeSpinner, () -> {
            generationProperties.getGeneral().setTime(timeSpinner.getValue());

            mainSceneController.getSamplesTabPaneController()
                    .getSampleTabControllers()
                    .forEach(SampleTabController::generateReports);
        });
    }

    private void handleEnvironmentTemperatureSpinnerLostFocus(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, environmentTemperatureSpinner, () -> {
            generationProperties.getGeneral().setEnvironmentTemperature(environmentTemperatureSpinner.getValue());

            mainSceneController.getSamplesTabPaneController()
                    .getSampleTabControllers()
                    .forEach(SampleTabController::generateReports);
        });
    }

    public void changeFireModeInclusion(Event event) {
        changeReportInclusion((CheckMenuItem) event.getTarget(), SampleTabController::getFireModeController);
    }

    public void changeExcessPressureInclusion(Event event) {
        changeReportInclusion((CheckMenuItem) event.getTarget(), SampleTabController::getExcessPressureController);
    }

    public void changeHeatFlowInclusion(Event event) {
        changeReportInclusion((CheckMenuItem) event.getTarget(), SampleTabController::getHeatFlowController);
    }

    public void changeUnheatedSurfaceInclusion(Event event) {
        changeReportInclusion((CheckMenuItem) event.getTarget(), SampleTabController::getUnheatedSurfaceController);
    }

    public void changeReportInclusion(CheckMenuItem menuItem, Function<SampleTabController, ReportInclusionChanger> mapper) {
        mainSceneController.getSamplesTabPaneController().getSampleTabControllers()
                .forEach(sampleTabController -> {
                    if (menuItem.isSelected()) {
                        mapper.apply(sampleTabController).includeReport();
                    } else {
                        mapper.apply(sampleTabController).excludeReport();
                    }
                });
    }
}
