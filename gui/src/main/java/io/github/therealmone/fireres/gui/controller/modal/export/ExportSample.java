package io.github.therealmone.fireres.gui.controller.modal.export;

import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@LoadableComponent("/component/modal/export/exportSample.fxml")
public class ExportSample extends AbstractComponent<CheckBox> {

    @FXML
    private MenuButton groupSelector;

    @FXML
    private TitledPane titledPane;

    @FXML
    @Getter
    private TextField fileName;

    @Setter
    @Getter
    private Sample sample;

    @Override
    public void postConstruct() {
        titledPane.setText(sample.getSampleProperties().getName());
        fileName.setText(sample.getSampleProperties().getName());
    }

    @FXML
    public void changeSampleInclusion() {
        if (getComponent().isSelected()) {
            titledPane.setDisable(false);
        } else {
            titledPane.setDisable(true);
            titledPane.setExpanded(false);
        }
    }

    public void addGroupSelector(ExportGroup group) {
        val groupItem = new MenuItem(group.getGroupName());

        groupItem.setOnAction(event -> {
            group.addSample(sample);
            ((ExportSampleColumn) getParent()).removeSample(this);
        });

        groupSelector.getItems().add(groupItem);
    }

    public void removeGroupSelector(ExportGroup group) {
        groupSelector.getItems().removeIf(menuItem -> menuItem.getText().equals(group.getGroupName()));
    }
}
