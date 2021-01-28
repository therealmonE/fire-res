package io.github.therealmone.fireres.gui.event;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.service.SampleCreatorService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
@RequiredArgsConstructor
public class AddSampleTabChangeListener implements ChangeListener<Tab> {

    @Inject
    private final SampleCreatorService sampleCreatorService;

    @Override
    public void changed(ObservableValue<? extends Tab> observableValue, Tab t1, Tab t2) {
        log.info("{} handling event", this.getClass().getSimpleName());

        if (isAddSampleTab(t2)) {
            val tabPane = t2.getTabPane();

            sampleCreatorService.createNewSample(tabPane);

            if (tabPane.getTabs().size() > 2) {
                tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
            }
        }
    }

    private boolean isAddSampleTab(Tab t2) {
        return t2 != null && t2.getId() != null && t2.getId().equals("addSampleTab");
    }

}
