package io.github.therealmone.fireres.gui.controller.modal.export;

import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

@LoadableComponent("/component/modal/export/groupedSample.fxml")
public class GroupedSample extends AbstractComponent<HBox> {

    @FXML
    private Label label;

    @Setter
    @Getter
    private Sample sample;

    @Override
    public void postConstruct() {
        label.setText(sample.getSampleProperties().getName());
    }

    public void remove() {
        ((ExportGroup) getParent()).removeSample(this);
    }
}
