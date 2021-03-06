package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.configurer.GeneralParametersConfigurer;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.ReportInclusionChanger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import lombok.Getter;

import java.util.function.Function;

@LoadableComponent("/component/common/general-params.fxml")
public class GeneralParams extends AbstractComponent<TitledPane> {

    @FXML
    @Getter
    private Spinner<Integer> time;

    @FXML
    @Getter
    private Spinner<Integer> environmentTemperature;

    @Inject
    private GeneralParametersConfigurer generalParametersConfigurer;

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void postConstruct() {
        generalParametersConfigurer.config(this);

        time.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleTimeSpinnerLostFocus(newValue));

        environmentTemperature.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleEnvironmentTemperatureSpinnerLostFocus(newValue));
    }

    private void handleTimeSpinnerLostFocus(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, time, () -> {
            generationProperties.getGeneral().setTime(time.getValue() + 1);

            findComponents(SampleTab.class).forEach(SampleTab::generateReports);
        });
    }

    private void handleEnvironmentTemperatureSpinnerLostFocus(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, environmentTemperature, () -> {
            generationProperties.getGeneral().setEnvironmentTemperature(environmentTemperature.getValue());

            findComponents(SampleTab.class).forEach(SampleTab::generateReports);
        });
    }

    public void changeFireModeInclusion(Event event) {
        changeReportInclusion((CheckMenuItem) event.getTarget(), SampleTab::getFireMode);
    }

    public void changeExcessPressureInclusion(Event event) {
        changeReportInclusion((CheckMenuItem) event.getTarget(), SampleTab::getExcessPressure);
    }

    public void changeHeatFlowInclusion(Event event) {
        changeReportInclusion((CheckMenuItem) event.getTarget(), SampleTab::getHeatFlow);
    }

    public void changeUnheatedSurfaceInclusion(Event event) {
        changeReportInclusion((CheckMenuItem) event.getTarget(), SampleTab::getUnheatedSurface);
    }

    public void changeReportInclusion(CheckMenuItem menuItem, Function<SampleTab, ReportInclusionChanger> mapper) {
        findComponents(SampleTab.class)
                .forEach(sampleTabController -> {
                    if (menuItem.isSelected()) {
                        mapper.apply(sampleTabController).includeReport();
                    } else {
                        mapper.apply(sampleTabController).excludeReport();
                    }
                });
    }
}
