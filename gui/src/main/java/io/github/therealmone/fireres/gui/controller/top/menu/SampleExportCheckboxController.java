package io.github.therealmone.fireres.gui.controller.top.menu;

import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SampleExportCheckboxController extends AbstractController {

    @FXML
    private CheckBox sampleExportCheckbox;

    @FXML
    private TitledPane sampleExportTitledPane;

    @FXML
    private TextField fileNameTextField;

    private Sample sample;

    private ExportModalWindowController exportModalWindowController;

    @Override
    public void postConstruct() {
        sampleExportTitledPane.setText(sample.getSampleProperties().getName());
        fileNameTextField.setText(sample.getSampleProperties().getName());
    }

    @FXML
    public void changeSampleInclusion() {
        if (sampleExportCheckbox.isSelected()) {
            sampleExportTitledPane.setDisable(false);
        } else {
            sampleExportTitledPane.setDisable(true);
            sampleExportTitledPane.setExpanded(false);
        }
    }
}
