package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.PresetChanger;
import io.github.therealmone.fireres.gui.controller.PresetContainer;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressure;
import io.github.therealmone.fireres.gui.controller.fire.mode.FireMode;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlow;
import io.github.therealmone.fireres.gui.controller.modal.SamplePresetChangeModalWindow;
import io.github.therealmone.fireres.gui.controller.modal.SampleRenameModalWindow;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurface;
import io.github.therealmone.fireres.gui.preset.Preset;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import io.github.therealmone.fireres.gui.service.PresetService;
import io.github.therealmone.fireres.gui.service.SampleService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
@LoadableComponent("/component/common/sample.fxml")
public class SampleTab extends AbstractComponent<Tab>
        implements SampleContainer, PresetContainer, PresetChanger {

    @FXML
    @Getter
    public TabPane reportsTabPane;

    @FXML
    @Getter
    private Tab unheatedSurfaceTab;

    @FXML
    @Getter
    private Tab heatFlowTab;

    @FXML
    @Getter
    private Tab excessPressureTab;

    @FXML
    @Getter
    private Tab fireModeTab;

    @Inject
    private SampleService sampleService;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @Setter
    private Sample sample;

    @FXML
    private ExcessPressure excessPressureController;

    @FXML
    private FireMode fireModeController;

    @FXML
    private UnheatedSurface unheatedSurfaceController;

    @FXML
    private HeatFlow heatFlowController;

    private Preset preset;

    @Inject
    private PresetService presetService;

    @Override
    public void postConstruct() {
        initializeSampleTabContextMenu();
        generateReports();
    }

    @Override
    public void changePreset(Preset preset) {
        this.preset = preset;

        getExcessPressure().changePreset(preset);
        getFireMode().changePreset(preset);
        getHeatFlow().changePreset(preset);
        getUnheatedSurface().changePreset(preset);
    }

    @FXML
    public void closeSample() {
        log.info("Close sample button pressed");

        val samplesTabPane = (SamplesTabPane) getParent();

        sampleService.closeSample(samplesTabPane, this);
    }

    @Override
    public Sample getSample() {
        return sample;
    }

    @Override
    public void generateReports() {
        getFireMode().createReport();
        getExcessPressure().createReport();
        getHeatFlow().createReport();
        getUnheatedSurface().createReport();
    }

    private void initializeSampleTabContextMenu() {
        val contextMenu = createSampleTabContextMenu();

        getComponent().setContextMenu(contextMenu);
    }

    private ContextMenu createSampleTabContextMenu() {
        val contextMenu = new ContextMenu();
        val addPointMenuItem = new MenuItem("Переименовать");
        val changePresetMenuItem = new MenuItem("Изменить пресет");

        addPointMenuItem.setOnAction(this::handleRenameEvent);
        changePresetMenuItem.setOnAction(this::handleChangePresetEvent);

        contextMenu.getItems().add(addPointMenuItem);
        contextMenu.getItems().add(changePresetMenuItem);

        return contextMenu;
    }

    private void handleChangePresetEvent(Event event) {
        fxmlLoadService.loadComponent(SamplePresetChangeModalWindow.class, this).getWindow().show();
    }

    private void handleRenameEvent(Event event) {
        fxmlLoadService.loadComponent(SampleRenameModalWindow.class, this).getWindow().show();
    }

    public FireMode getFireMode() {
        return fireModeController;
    }

    public ExcessPressure getExcessPressure() {
        return excessPressureController;
    }

    public HeatFlow getHeatFlow() {
        return heatFlowController;
    }

    public UnheatedSurface getUnheatedSurface() {
        return unheatedSurfaceController;
    }

    @Override
    public Preset getPreset() {
        if (this.preset == null) {
            this.preset = presetService.getDefaultPreset();
        }

        return preset;
    }
}


