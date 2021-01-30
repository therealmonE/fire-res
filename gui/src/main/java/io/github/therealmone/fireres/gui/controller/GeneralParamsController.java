package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeneralParamsController extends AbstractController {

    @FXML
    private Spinner<Integer> timeSpinner;

    @FXML
    private Spinner<Integer> environmentTemperatureSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Override
    public void initialize() {
        resetSettingsService.resetTime(timeSpinner);
        resetSettingsService.resetEnvironmentTemperature(environmentTemperatureSpinner);
    }
}
