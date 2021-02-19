package io.github.therealmone.fireres.gui.controller.top.menu;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.service.ExportService;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExportModalWindowController extends AbstractController {

    private SamplesTabPaneController samplesTabPaneController;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @FXML
    private VBox samplesExportVbox;

    @Inject
    private ExportService exportService;

    public List<SampleExportCheckboxController> exportCheckboxControllers = new ArrayList<>();

    @Override
    public void postConstruct() {
        samplesTabPaneController.getSampleTabControllers().stream()
                .map(SampleTabController::getSample)
                .forEach(sample -> {
                    val checkBox = fxmlLoadService.loadSampleExportCheckbox(sample, this);

                    samplesExportVbox.getChildren().add(checkBox);
                });
    }

    @FXML
    public void exportSamples() {
        val dirChooser = new DirectoryChooser();

        val chosenDir = dirChooser.showDialog(samplesTabPaneController
                .getMainSceneController()
                .getPrimaryStage());

        if (chosenDir != null) {
            exportCheckboxControllers.forEach(controller -> {
                if (controller.getSampleExportCheckbox().isSelected()) {
                    exportService.exportReports(
                            chosenDir.toPath(),
                            controller.getFileNameTextField().getText(),
                            Collections.singletonList(controller.getSample()));
                }
            });
        }
    }
}
