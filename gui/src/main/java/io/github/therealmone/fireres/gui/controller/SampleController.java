package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.service.SampleService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Data
public class SampleController implements Initializable {

    //injected by FxmlLoader
    private TabPane samplesTabPane;

    @FXML
    private Tab sampleTab;

    @Inject
    private SampleService sampleService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("Initializing: {}", this.getClass().getSimpleName());
    }

    @FXML
    public void closeSample(Event event) {
        log.info("Close sample button pressed");
        val closedSample = (Tab) event.getTarget();

        sampleService.closeSample(samplesTabPane, closedSample);
    }
}
