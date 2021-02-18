package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.service.ExportService;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

@EqualsAndHashCode(callSuper = true)
@Data
public class TopMenuBarController extends AbstractController {

    public static final String USER_GUIDE_LINK = "https://github.com/therealmonE/fire-res/wiki";

    @ParentController
    private MainSceneController mainSceneController;

    @Inject
    private ExportService exportService;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @FXML
    private MenuItem aboutProgramMenuItem;

    @FXML
    private MenuItem userGuideMenuItem;

    @Inject
    private HostServices hostServices;

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

    @FXML
    public void openExportModalWindow() {
        fxmlLoadService.loadExportModalWindow(this).show();
    }

    @FXML
    public void openAboutProgramModalWindow() {
        fxmlLoadService.loadAboutProgramModalWindow(this).show();
    }

    @FXML
    public void openUserGuideLink() {
        hostServices.showDocument(USER_GUIDE_LINK);
    }

}
