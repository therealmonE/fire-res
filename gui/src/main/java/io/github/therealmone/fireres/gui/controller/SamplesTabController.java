package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.annotation.Time;
import io.github.therealmone.fireres.gui.event.AddSampleTabChanged;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class SamplesTabController implements Initializable {

    @FXML
    private TabPane samplesTabPane;

    @Inject
    @Time
    private Integer time;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        samplesTabPane.getSelectionModel().selectedItemProperty().addListener(new AddSampleTabChanged());
    }

}
