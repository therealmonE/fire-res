package io.github.therealmone.fireres.gui.controller.modal.export;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.annotation.ModalWindow;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.common.MainScene;
import io.github.therealmone.fireres.gui.controller.common.SampleTab;
import io.github.therealmone.fireres.gui.service.ExportService;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.val;

import java.util.Collections;
import java.util.List;

@ModalWindow(title = "Экспорт")
@LoadableComponent("/component/top-menu/exportModalWindow.fxml")
public class ExportModalWindow extends AbstractComponent<AnchorPane> {

    @ModalWindow.Window
    @Getter
    private Stage window;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @FXML
    private VBox exportSamplesVbox;

    @Inject
    private ExportService exportService;

    @Override
    public void postConstruct() {
        ((MainScene) getParent()).getSamplesTabPane().getSampleTabs().stream()
                .map(SampleTab::getSample)
                .forEach(sample -> {
                    val exportSample = fxmlLoadService.loadComponent(ExportSample.class, this,
                            component -> component.setSample(sample));

                    exportSamplesVbox.getChildren().add(exportSample.getComponent());
                });
    }

    @FXML
    public void exportSamples() {
        val dirChooser = new DirectoryChooser();

        val chosenDir = dirChooser.showDialog(((MainScene) getParent()).getPrimaryStage());

        if (chosenDir != null) {
            getExportSamples().forEach(controller -> {
                if (controller.getComponent().isSelected()) {
                    exportService.exportReports(
                            chosenDir.toPath(),
                            controller.getFileName().getText(),
                            Collections.singletonList(controller.getSample()));
                }
            });
        }
    }

    public List<ExportSample> getExportSamples() {
        return getChildren(ExportSample.class);
    }

}
