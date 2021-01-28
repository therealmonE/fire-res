package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralParamsController implements Initializable {

    @FXML
    private Spinner<Integer> timeSpinner;

    @FXML
    private Spinner<Integer> environmentTemperatureSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetSettingsService.resetTime(timeSpinner);
        resetSettingsService.resetEnvironmentTemperature(environmentTemperatureSpinner);
    }
}
