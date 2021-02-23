package io.github.therealmone.fireres.gui.controller.modal.export;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import lombok.val;

@LoadableComponent("/component/modal/export/exportGroup.fxml")
public class ExportGroup extends AbstractComponent<TitledPane> {

    @FXML
    private TextField fileNameField;

    @FXML
    private VBox groupedSamplesVbox;

    @Inject
    private FxmlLoadService fxmlLoadService;

    public void setGroupName(String groupName) {
        this.getComponent().setText(groupName);
        setFileName(groupName);
    }

    public void setFileName(String fileName) {
        fileNameField.setText(fileName);
    }

    public String getGroupName() {
        return this.getComponent().getText();
    }

    public void addSample(Sample sample) {
        val groupedSample = fxmlLoadService.loadComponent(GroupedSample.class, this,
                component -> component.setSample(sample));

        groupedSamplesVbox.getChildren().add(groupedSample.getComponent());
        groupedSample.getComponent().requestFocus();
    }

    public void removeSample(GroupedSample groupedSample) {
        getChildren().removeIf(child -> child.equals(groupedSample));
        groupedSamplesVbox.getChildren().removeIf(child -> child.equals(groupedSample.getComponent()));
        ((ExportModalWindow) getParent().getParent()).addSample(groupedSample.getSample());
    }

    @FXML
    public void remove() {
        ((ExportGroupColumn) getParent()).removeGroup(this);
    }
}
