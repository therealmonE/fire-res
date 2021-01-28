package io.github.therealmone.fireres.gui.service;

import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;

public interface ResetSettingsService {

    void resetSamples(TabPane samplesTabPane);

    void resetEnvironmentTemperature(Spinner<Integer> environmentTemperatureSpinner);

    void resetTime(Spinner<Integer> timeSpinner);

}
