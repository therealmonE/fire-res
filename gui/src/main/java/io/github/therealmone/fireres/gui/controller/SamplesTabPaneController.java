package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.gui.service.SampleService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class SamplesTabPaneController extends AbstractController {

    private MainSceneController mainSceneController;

    @FXML
    private TabPane samplesTabPane;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private SampleService sampleService;

    /**
     * Injected via {@link io.github.therealmone.fireres.gui.service.FxmlLoadService}
     * @see io.github.therealmone.fireres.gui.service.FxmlLoadService#loadSampleTab(SamplesTabPaneController, Object)
     */
    private List<SampleTabController> sampleTabControllers = new ArrayList<>();

    @Override
    public void postConstruct() {
        resetSettingsService.resetSamples(this);
    }

    @FXML
    public void addSample(Event event) {
        if (samplesTabPane.getTabs().size() == 1) {
            return;
        }

        log.info("Add sample tab pressed");

        val target = (Tab) event.getTarget();

        if (isAddSampleTab(target)) {
            sampleService.createNewSample(this);

            if (samplesTabPane.getTabs().size() > 2) {
                samplesTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
            }
        }
    }

    private boolean isAddSampleTab(Tab t2) {
        return t2 != null && t2.getId() != null && t2.getId().equals("addSampleTab");
    }

}
