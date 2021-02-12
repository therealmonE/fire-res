package io.github.therealmone.fireres.gui.controller.common;

import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
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

        getSample().getSampleProperties().setName(renameTextField.getText());
        sampleTabController.getSampleTab().setText(renameTextField.getText());
    }

    @Override
    public Sample getSample() {
        return sampleTabController.getSample();
    }
}
