package io.github.therealmone.fireres.gui.controller.modal;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.annotation.ModalWindow;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.PresetChanger;
import io.github.therealmone.fireres.gui.controller.PresetContainer;
import io.github.therealmone.fireres.gui.preset.Preset;
import io.github.therealmone.fireres.gui.service.AlertService;
import io.github.therealmone.fireres.gui.service.PresetService;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;

@ModalWindow(title = "Изменение пресета")
@LoadableComponent("/component/modal/samplePresetChangeModalWindow.fxml")
public class SamplePresetChangeModalWindow extends AbstractComponent<Pane> {

    @ModalWindow.Window
    @Getter
    private Stage window;

    @FXML
    public ChoiceBox<Preset> presets;

    @Inject
    private AlertService alertService;

    @Inject
    private PresetService presetService;

    @Override
    public void postConstruct() {
        presets.getSelectionModel().selectedItemProperty().addListener((observableValue, preset, t1) ->
                handleAnotherPresetChosen());

        presets.getItems().addAll(presetService.getAvailablePresets());

        choosePreset(((PresetContainer) getParent()).getPreset());
    }

    @FXML
    public void closeWindow() {
        window.close();
    }

    @FXML
    public void changePreset() {
        alertService.showConfirmation(
                "Внимание! При изменении пресета все параметры " +
                        "в образце будут сброшены, вы хотите продолжить?",
                this::handlePresetChangeConfirmed);
    }

    private void handlePresetChangeConfirmed() {
        ((PresetChanger) getParent()).changePreset(presets.getValue());
    }

    private void choosePreset(Preset preset) {
        presets.getSelectionModel().select(preset);
        presets.setTooltip(new Tooltip(preset.getDescription()));
    }

    private void handleAnotherPresetChosen() {
        choosePreset(presets.getValue());
    }
}
