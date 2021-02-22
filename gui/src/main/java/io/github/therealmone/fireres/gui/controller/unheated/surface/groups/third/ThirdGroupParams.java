package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceThirdGroupService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import lombok.Getter;

import java.util.UUID;

@LoadableComponent("/component/unheated-surface/groups/third/thirdGroupParams.fxml")
public class ThirdGroupParams extends AbstractReportUpdaterComponent<TitledPane>
        implements UnheatedSurfaceReportContainer {

    @FXML
    @Getter
    private Spinner<Integer> thermocouples;

    @FXML
    @Getter
    private Spinner<Integer> bound;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private UnheatedSurfaceThirdGroupService unheatedSurfaceThirdGroupService;

    @Override
    protected void initialize() {
        thermocouples.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocouplesCountSpinnerFocusChanged(newValue));

        bound.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleThermocouplesBoundSpinnerFocusChanged(newValue));
    }

    private void handleThermocouplesCountSpinnerFocusChanged(Boolean focusValue) {
        Runnable action = () ->
                unheatedSurfaceThirdGroupService.updateThermocoupleCount(
                        getReport(),
                        thermocouples.getValue());

        handleSpinnerLostFocus(focusValue, thermocouples, () ->
                updateReport(action, ((ThirdGroup) getParent()).getParamsVbox()));
    }

    private void handleThermocouplesBoundSpinnerFocusChanged(Boolean focusValue) {
        Runnable action = () ->
                unheatedSurfaceThirdGroupService.updateBound(
                        getReport(),
                        bound.getValue());

        handleSpinnerLostFocus(focusValue, bound, () ->
                updateReport(action, ((ThirdGroup) getParent()).getParamsVbox()));
    }

    @Override
    public void postConstruct() {
        resetSettingsService.resetThirdThermocoupleGroupParameters(this);
    }

    @Override
    public Sample getSample() {
        return ((ThirdGroup) getParent()).getSample();
    }

    @Override
    public UnheatedSurfaceReport getReport() {
        return ((ThirdGroup) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return ((ThirdGroup) getParent()).getChartContainer();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getThirdGroup().getId();
    }
}
