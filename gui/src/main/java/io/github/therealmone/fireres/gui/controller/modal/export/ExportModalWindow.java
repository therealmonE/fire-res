package io.github.therealmone.fireres.gui.controller.modal.export;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.annotation.ModalWindow;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.common.MainScene;
import io.github.therealmone.fireres.gui.controller.common.SampleTab;
import io.github.therealmone.fireres.gui.service.ExportService;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.val;

import java.io.File;
import java.util.Collections;
import java.util.stream.Collectors;

@ModalWindow(title = "Экспорт")
@LoadableComponent("/component/modal/export/exportModalWindow.fxml")
public class ExportModalWindow extends AbstractComponent<AnchorPane> {

    @FXML
    private HBox exportColumns;

    @ModalWindow.Window
    @Getter
    private Stage window;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @Inject
    private ExportService exportService;

    @Getter
    private ExportSampleColumn exportSampleColumn;

    @Getter
    private ExportGroupColumn exportGroupColumn;

    @Override
    public void postConstruct() {
        val initialSamples = ((MainScene) getParent()).getSamplesTabPane().getSampleTabs().stream()
                .map(SampleTab::getSample)
                .collect(Collectors.toList());

        initialSamples.forEach(this::addSample);
    }

    @FXML
    public void exportSamples() {
        val dirChooser = new DirectoryChooser();

        val chosenDir = dirChooser.showDialog(window);

        if (chosenDir != null) {
            if (exportSampleColumn != null) {
                exportSamples(chosenDir);
            }

            if (exportGroupColumn != null) {
                exportGroups(chosenDir);
            }
        }
    }

    private void exportSamples(File chosenDir) {
        exportSampleColumn.getChildren(ExportSample.class).forEach(exportSample -> {
            if (exportSample.getComponent().isSelected()) {
                exportService.exportReports(
                        chosenDir.toPath(),
                        exportSample.getFileName().getText(),
                        Collections.singletonList(exportSample.getSample()));
            }
        });
    }

    private void exportGroups(File chosenDir) {
        exportGroupColumn.getChildren(ExportGroup.class).forEach(exportGroup -> {
            exportService.exportReports(
                    chosenDir.toPath(),
                    exportGroup.getFileName(),
                    exportGroup.getSamples());
        });
    }

    public void addSample(Sample sample) {
        if (exportSampleColumn == null) {
            addExportSampleColumn();
        }

        val exportSample = exportSampleColumn.addSample(sample);

        if (exportGroupColumn != null) {
            exportGroupColumn.getChildren(ExportGroup.class).forEach(exportSample::addGroupSelector);
        }

        window.sizeToScene();
    }

    @FXML
    public void addGroup() {
        if (exportGroupColumn == null) {
            addExportGroupColumn();
        }

        val group = exportGroupColumn.addGroup();

        if (exportSampleColumn != null) {
            exportSampleColumn.addGroupSelector(group);
        }

        window.sizeToScene();
    }

    private void addExportSampleColumn() {
        val exportSampleColumn = fxmlLoadService.loadComponent(ExportSampleColumn.class, this);
        this.exportSampleColumn = exportSampleColumn;

        exportColumns.getChildren().add(0, exportSampleColumn.getComponent());
    }

    private void addExportGroupColumn() {
        val exportGroupColumn = fxmlLoadService.loadComponent(ExportGroupColumn.class, this);
        this.exportGroupColumn = exportGroupColumn;

        exportColumns.getChildren().add(1, exportGroupColumn.getComponent());
    }

    public void removeExportSampleColumn(ExportSampleColumn exportSampleColumn) {
        getChildren().removeIf(child -> child.equals(exportSampleColumn));

        this.exportSampleColumn = null;
        this.exportColumns.getChildren().removeIf(child -> child.equals(exportSampleColumn.getComponent()));

        window.sizeToScene();
    }

    public void removeExportGroupColumn(ExportGroupColumn exportGroupColumn) {
        getChildren().removeIf(child -> child.equals(exportGroupColumn));

        this.exportGroupColumn = null;
        this.exportColumns.getChildren().removeIf(child -> child.equals(exportGroupColumn.getComponent()));

        window.sizeToScene();
    }
}
