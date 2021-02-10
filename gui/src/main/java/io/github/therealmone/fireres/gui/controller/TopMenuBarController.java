package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.service.ExportService;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

@EqualsAndHashCode(callSuper = true)
@Data
public class TopMenuBarController extends AbstractController {

    @ParentController
    private MainSceneController mainSceneController;

    @Inject
    private ExportService exportService;

    @FXML
    public void exportSamples() {
        val dirChooser = new DirectoryChooser();

        val chosenDir = dirChooser.showDialog(mainSceneController.getPrimaryStage());

        if (chosenDir != null) {
            exportService.exportReports(
                    chosenDir.toPath(),
                    mainSceneController.getSamplesTabPaneController().getSampleTabControllers());
        }
    }

}
