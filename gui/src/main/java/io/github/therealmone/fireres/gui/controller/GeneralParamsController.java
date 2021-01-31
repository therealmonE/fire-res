package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class GeneralParamsController extends AbstractController {

    private MainSceneController mainSceneController;

    @FXML
    private Spinner<Integer> timeSpinner;

    @FXML
    private Spinner<Integer> environmentTemperatureSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Override
    public void postConstruct() {
        resetSettingsService.resetGeneralParameters(this);
    }
}
