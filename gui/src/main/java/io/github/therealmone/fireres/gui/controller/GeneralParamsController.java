package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Spinner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class GeneralParamsController extends AbstractController {

    @ParentController
    private MainSceneController mainSceneController;

    @FXML
    private Spinner<Integer> timeSpinner;

    @FXML
    private Spinner<Integer> environmentTemperatureSpinner;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Override
    public void postConstruct() {
        resetSettingsService.resetGeneralParameters(this);
    }

    public void changeFireModeInclusion(Event event) {
        changeReportInclusion((CheckMenuItem) event.getTarget(), SampleTabController::getFireModePaneController);
    }

    public void changeExcessPressureInclusion(Event event) {
        changeReportInclusion((CheckMenuItem) event.getTarget(), SampleTabController::getExcessPressurePaneController);
    }

    public void changeHeatFlowInclusion(Event event) {
        changeReportInclusion((CheckMenuItem) event.getTarget(), SampleTabController::getHeatFlowPaneController);
    }

    public void changeUnheatedSurfaceInclusion(Event event) {
        changeReportInclusion((CheckMenuItem) event.getTarget(), SampleTabController::getUnheatedSurfacePaneController);
    }

    public void changeReportInclusion(CheckMenuItem menuItem, Function<SampleTabController, ReportInclusionChanger> mapper) {
        mainSceneController.getSamplesTabPaneController().getSampleTabControllers()
                .forEach(sampleTabController -> {
                    if (menuItem.isSelected()) {
                        mapper.apply(sampleTabController).includeReport();
                    } else {
                        mapper.apply(sampleTabController).excludeReport();
                    }
                });
    }
}
