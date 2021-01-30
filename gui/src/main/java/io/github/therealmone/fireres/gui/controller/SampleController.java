package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.service.SampleService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class SampleController extends AbstractController {

    @FXML
    private Tab sampleTab;

    @Inject
    private SampleService sampleService;

    @Override
    public void initialize() {
    }

    @FXML
    public void closeSample(Event event) {
        log.info("Close sample button pressed");
        val closedSample = (Tab) event.getTarget();

        sampleService.closeSample(closedSample);
    }
}
