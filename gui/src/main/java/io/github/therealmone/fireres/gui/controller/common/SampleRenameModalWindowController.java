package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.service.AlertService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class SampleRenameModalWindowController extends AbstractController implements SampleContainer {

    @ParentController
    private SampleTabController sampleTabController;

    @FXML
    private TextField renameTextField;

    private Stage modalWindow;

    @Inject
    private AlertService alertService;

    @Override
    protected void initialize() {
        renameTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                renameSample();
            }
        });
    }

    @Override
    public void postConstruct() {
        val currentSampleName = getSample().getSampleProperties().getName();

        renameTextField.setText(currentSampleName);
    }

    public void closeWindow() {
        modalWindow.close();
    }

    public void renameSample() {
        log.info("Rename sample button pressed");

        if (validateSampleName()) {
            getSample().getSampleProperties().setName(renameTextField.getText());
            sampleTabController.getSampleTab().setText(renameTextField.getText());
        }
    }

    private boolean validateSampleName() {
        val samplesControllers = sampleTabController.getSamplesTabPaneController().getSampleTabControllers();
        val newSampleName = renameTextField.getText();
        val currentSample = getSample();

        if (newSampleName == null || newSampleName.isEmpty() || newSampleName.isBlank()) {
            alertService.showError("Имя образца не может быть пустым");
            return false;
        }

        for (SampleTabController sampleController : samplesControllers) {
            val sample = sampleController.getSample();

            if (currentSample.equals(sample)) {
                continue;
            }

            if (newSampleName.equals(sample.getSampleProperties().getName())) {
                alertService.showError("Образец с таким именем уже существует");
                return false;
            }
        }

        return true;
    }

    @Override
    public Sample getSample() {
        return sampleTabController.getSample();
    }
}
