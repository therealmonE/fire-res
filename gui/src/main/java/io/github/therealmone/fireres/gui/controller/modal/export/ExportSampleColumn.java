package io.github.therealmone.fireres.gui.controller.modal.export;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.val;

@LoadableComponent("/component/modal/export/exportSampleColumn.fxml")
public class ExportSampleColumn extends AbstractComponent<VBox> {

    @FXML
    private VBox exportSamplesVbox;

    @Inject
    private FxmlLoadService fxmlLoadService;

    public void addGroupSelector(ExportGroup group) {
        getChildren(ExportSample.class).forEach(exportSample -> exportSample.addGroupSelector(group));
    }

    public void removeGroupSelector(ExportGroup group) {
        getChildren(ExportSample.class).forEach(exportSample -> exportSample.removeGroupSelector(group));
    }

    public ExportSample addSample(Sample sample) {
        val exportSample = fxmlLoadService.loadComponent(ExportSample.class, this,
                component -> component.setSample(sample));

        exportSamplesVbox.getChildren().add(exportSample.getComponent());

        return exportSample;
    }

    public void removeSample(ExportSample exportSample) {
        getChildren().removeIf(child -> child.equals(exportSample));
        exportSamplesVbox.getChildren().removeIf(child -> child.equals(exportSample.getComponent()));

        if (getChildren().isEmpty()) {
            ((ExportModalWindow) getParent()).removeExportSampleColumn(this);
        }
    }

}
