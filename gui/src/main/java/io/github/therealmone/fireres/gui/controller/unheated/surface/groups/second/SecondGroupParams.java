package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.second;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportUpdater;
import io.github.therealmone.fireres.gui.controller.Resettable;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceSecondGroupService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import lombok.Getter;

import java.util.UUID;

@LoadableComponent("/component/unheated-surface/groups/second/secondGroupParams.fxml")
public class SecondGroupParams extends AbstractReportUpdaterComponent<TitledPane>
        implements UnheatedSurfaceReportContainer, Resettable {

    @FXML
    @Getter
    private Spinner<Integer> thermocouples;

    @FXML
    @Getter
    private Spinner<Integer> bound;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private UnheatedSurfaceSecondGroupService unheatedSurfaceSecondGroupService;

    @Override
    protected void initialize() {
        thermocouples.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocouplesCountSpinnerFocusChanged(newValue));

        bound.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocouplesBoundSpinnerFocusChanged(newValue));
    }

    @Override
    public void postConstruct() {
        reset();
    }

    @Override
    public void reset() {
        resetSettingsService.resetSecondThermocoupleGroupParameters(this);
    }

    private void handleThermocouplesCountSpinnerFocusChanged(Boolean focusValue) {
        Runnable action = () ->
                unheatedSurfaceSecondGroupService.updateThermocoupleCount(
                        getReport(),
                        thermocouples.getValue());

        handleSpinnerLostFocus(focusValue, thermocouples, () ->
                updateReport(action, ((SecondGroup) getParent()).getParamsVbox()));
    }

    private void handleThermocouplesBoundSpinnerFocusChanged(Boolean focusValue) {
        Runnable action = () ->
                unheatedSurfaceSecondGroupService.updateBound(
                        getReport(),
                        bound.getValue());

        handleSpinnerLostFocus(focusValue, bound, () ->
                updateReport(action, ((SecondGroup) getParent()).getParamsVbox()));
    }

    @Override
    public Sample getSample() {
        return ((SecondGroup) getParent()).getSample();
    }

    @Override
    public UnheatedSurfaceReport getReport() {
        return ((SecondGroup) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return ((SecondGroup) getParent()).getChartContainer();
    }

    @Override
    public UUID getUpdatingElementId() {
        return ((ReportUpdater) getParent()).getUpdatingElementId();
    }

}
