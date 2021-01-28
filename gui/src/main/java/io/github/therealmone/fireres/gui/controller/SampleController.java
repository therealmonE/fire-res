package io.github.therealmone.fireres.gui.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class SampleController {

    @FXML
    private TabPane samplesTabPane;

    public void handleSampleClosed(Event event) {
        System.out.println(samplesTabPane);
    }

}
