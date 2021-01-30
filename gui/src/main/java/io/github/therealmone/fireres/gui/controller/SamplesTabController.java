package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.gui.service.SampleService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class SamplesTabController extends AbstractController {

    @FXML
    private TabPane samplesTabPane;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private SampleService sampleService;

    @Override
    public void initialize() {
        resetSettingsService.resetSamples(samplesTabPane);
    }

    @FXML
    public void addSample(Event event) {
        if (samplesTabPane.getTabs().size() == 1) {
            return;
        }

        log.info("Add sample tab pressed");

        val target = (Tab) event.getTarget();

        if (isAddSampleTab(target)) {
            val tabPane = target.getTabPane();

            sampleService.createNewSample();

            if (tabPane.getTabs().size() > 2) {
                tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
            }
        }
    }

    private boolean isAddSampleTab(Tab t2) {
        return t2 != null && t2.getId() != null && t2.getId().equals("addSampleTab");
    }

}
