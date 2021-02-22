package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.gui.service.SampleService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

@Slf4j
@LoadableComponent("/component/samplesTabPane.fxml")
public class SamplesTabPane extends AbstractComponent<TabPane> {

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private SampleService sampleService;

    @Override
    public void postConstruct() {
        resetSettingsService.resetSamples(this);
    }

    @FXML
    public void addSample(Event event) {
        if (getComponent().getTabs().size() == 1) {
            return;
        }

        log.info("Add sample tab pressed");

        val target = (Tab) event.getTarget();

        if (isAddSampleTab(target)) {
            sampleService.createNewSample(this);

            if (getComponent().getTabs().size() > 2) {
                getComponent().setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
            }
        }
    }

    private boolean isAddSampleTab(Tab t2) {
        return t2 != null && t2.getId() != null && t2.getId().equals("addSampleTab");
    }

    public List<SampleTab> getSampleTabs() {
        return getChildren(SampleTab.class);
    }

}
