package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressureController;
import io.github.therealmone.fireres.gui.controller.fire.mode.FireModeController;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlowController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceController;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import io.github.therealmone.fireres.gui.service.SampleService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class SampleTabController extends AbstractController implements SampleContainer {

    /**
     * Injected via {@link io.github.therealmone.fireres.gui.service.FxmlLoadService}
     *
     * @see io.github.therealmone.fireres.gui.service.FxmlLoadService#loadSampleTab(SamplesTabPaneController, Object)
     */
    @ParentController
    private SamplesTabPaneController samplesTabPaneController;

    @Inject
    private SampleService sampleService;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @FXML
    private Tab sampleTab;

    @FXML
    private Tab fireModeTab;

    @FXML
    private Tab excessPressureTab;

    @FXML
    private Tab heatFlowTab;

    @FXML
    private Tab unheatedSurfaceTab;

    @FXML
    private TabPane reportsTabPane;

    @FXML
    @ChildController
    private ExcessPressureController excessPressureController;

    @FXML
    @ChildController
    private FireModeController fireModeController;

    @FXML
    private UnheatedSurfaceController unheatedSurfaceController;

    @FXML
    @ChildController
    private HeatFlowController heatFlowController;

    @Override
    public void initialize() {
        excessPressureController.setSampleTabController(this);
        heatFlowController.setSampleTabController(this);
        fireModeController.setSampleTabController(this);
        unheatedSurfaceController.setSampleTabController(this);

    }

    @Override
    public void postConstruct() {
        excessPressureController.postConstruct();
        heatFlowController.postConstruct();
        fireModeController.postConstruct();
        unheatedSurfaceController.postConstruct();

        initializeSampleTabContextMenu();

        generateReports();

    }

    @FXML
    public void closeSample(Event event) {
        log.info("Close sample button pressed");

        val closedSample = (Tab) event.getTarget();
        val samplesTabPane = samplesTabPaneController.getSamplesTabPane();

        samplesTabPaneController.getSampleTabControllers().removeIf(this::equals);
        sampleService.closeSample(samplesTabPane, closedSample);
    }

    @Override
    public Sample getSample() {
        return sampleService.getSample(sampleTab);
    }

    @Override
    public void generateReports() {
        fireModeController.createReport();
        excessPressureController.createReport();
        heatFlowController.createReport();
        unheatedSurfaceController.createReport();
    }

    private void initializeSampleTabContextMenu() {
        val contextMenu = createSampleTabContextMenu();

        sampleTab.setContextMenu(contextMenu);
    }

    private ContextMenu createSampleTabContextMenu() {
        val contextMenu = new ContextMenu();
        val addPointMenuItem = new MenuItem("Переименовать образец");

        addPointMenuItem.setOnAction(this::handleRenameEvent);
        contextMenu.getItems().add(addPointMenuItem);

        return contextMenu;
    }

    private void handleRenameEvent(Event event) {
        fxmlLoadService.loadRenameSampleModalWindow(this).show();
    }
}
