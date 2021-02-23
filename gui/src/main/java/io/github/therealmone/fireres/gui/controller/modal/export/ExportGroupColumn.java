package io.github.therealmone.fireres.gui.controller.modal.export;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.val;

import java.util.concurrent.atomic.AtomicInteger;

@LoadableComponent("/component/modal/export/exportGroupColumn.fxml")
public class ExportGroupColumn extends AbstractComponent<VBox> {

    private static final String GROUP_NAME_TEMPLATE = "Группа №%d";

    @FXML
    private VBox groupsVbox;

    @Inject
    private FxmlLoadService fxmlLoadService;

    private final AtomicInteger groupCount = new AtomicInteger(0);

    public ExportGroup addGroup() {
        val group = fxmlLoadService.loadComponent(ExportGroup.class, this);

        group.setGroupName(String.format(GROUP_NAME_TEMPLATE, groupCount.incrementAndGet()));
        groupsVbox.getChildren().add(group.getComponent());

        return group;
    }

    public void removeGroup(ExportGroup group) {
        getChildren().removeIf(child -> child.equals(group));
        groupsVbox.getChildren().removeIf(child -> child.equals(group.getComponent()));

        val parent = (ExportModalWindow) getParent();

        group.getChildren(GroupedSample.class).forEach(groupedSample -> parent.addSample(groupedSample.getSample()));

        if (parent.getExportSampleColumn() != null) {
            parent.getExportSampleColumn().removeGroupSelector(group);
        }

        if (getChildren().isEmpty()) {
            parent.removeExportGroupColumn(this);
        }
    }
}
