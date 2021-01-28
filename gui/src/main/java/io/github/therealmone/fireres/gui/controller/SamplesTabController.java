package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.event.AddSampleTabChangeListener;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.gui.service.SampleCreatorService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SamplesTabController implements Initializable {

    @FXML
    private TabPane samplesTabPane;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private SampleCreatorService sampleCreatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetSettingsService.resetSamples(samplesTabPane);
        samplesTabPane.getSelectionModel().selectedItemProperty().addListener(
                new AddSampleTabChangeListener(sampleCreatorService));
    }

}
