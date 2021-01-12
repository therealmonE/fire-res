package io.github.therealmone.fireres.gui.event;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class AddSampleTabChanged implements ChangeListener<Tab> {

    @Override
    public void changed(ObservableValue<? extends Tab> observableValue, Tab t1, Tab t2) {
        log.info("AddSampleTabChanged event processing");

        if (isAddSampleTab(t2)) {
            val tabPane = t2.getTabPane();

            val addedTab = addNewSample(tabPane);

            t1.getTabPane().getSelectionModel().select(addedTab);
        }
    }

    private boolean isAddSampleTab(Tab t2) {
        return t2.getId() != null && t2.getId().equals("addSampleTab");
    }

    private Tab addNewSample(TabPane tabPane) {
        val newTab = new Tab("new tab");

        tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab);

        return newTab;
    }

}
