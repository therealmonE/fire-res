package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressurePaneController;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlowPaneController;
import io.github.therealmone.fireres.gui.service.SampleService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
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
     * @see io.github.therealmone.fireres.gui.service.FxmlLoadService#loadSampleTab(SamplesTabPaneController, Object)
     */
    @ParentController
    private SamplesTabPaneController samplesTabPaneController;

    @Inject
    private SampleService sampleService;

    @FXML
    @ChildController
    private HeatFlowPaneController heatFlowPaneController;

    @FXML
    private Tab sampleTab;

    @FXML
    @ChildController
    private ExcessPressurePaneController excessPressurePaneController;

    @Override
    public void initialize() {
        excessPressurePaneController.setSampleTabController(this);
        heatFlowPaneController.setSampleTabController(this);
    }

    @Override
    public void postConstruct() {
        excessPressurePaneController.postConstruct();
        heatFlowPaneController.postConstruct();
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
    public SampleProperties getSampleProperties() {
        return sampleService.getSampleProperties(sampleTab);
    }

}
