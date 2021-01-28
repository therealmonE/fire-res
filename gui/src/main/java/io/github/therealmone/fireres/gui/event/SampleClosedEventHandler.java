package io.github.therealmone.fireres.gui.event;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
@RequiredArgsConstructor
public class SampleClosedEventHandler implements EventHandler<Event> {

    private final TabPane samplesTabPane;
    private final GenerationProperties generationProperties;

    @Override
    public void handle(Event event) {
        log.info("{} handling event", this.getClass().getSimpleName());
        val closedSampleTab = (Tab) event.getTarget();
        val sampleId = ((SampleProperties) closedSampleTab.getUserData()).getId();

        if (generationProperties.getSamples().removeIf(sample -> sample.getId().equals(sampleId))) {
            renameSamples();
        }

        if (samplesTabPane.getTabs().size() == 2) {
            samplesTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        }
    }

    private void renameSamples() {
        val samplesTabs = samplesTabPane.getTabs();

        for (int i = 0; i < generationProperties.getSamples().size(); i++) {
            val sampleProperties = generationProperties.getSamples().get(i);
            val sampleTab = samplesTabs.stream()
                    .filter(tab ->
                            ((SampleProperties) tab.getUserData()).getId().equals(sampleProperties.getId()))
                    .findFirst()
                    .orElseThrow();

            sampleTab.setText("Образец №" + (i + 1));
        }

        samplesTabs.sort((t1, t2) -> {
            if (t1.getId() != null && t1.getId().equals("addSampleTab")) {
                return 1;
            } else if (t2.getId() != null && t2.getId().equals("addSampleTab")) {
                return -1;
            } else {
                return t1.getText().compareTo(t2.getText());
            }
        });
    }

}
