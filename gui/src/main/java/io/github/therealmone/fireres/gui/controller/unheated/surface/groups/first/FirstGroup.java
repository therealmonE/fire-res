package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.config.ExcessPressureProperties;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.Resettable;
import io.github.therealmone.fireres.gui.controller.common.BoundsShiftParams;
import io.github.therealmone.fireres.gui.controller.common.FunctionParams;
import io.github.therealmone.fireres.gui.controller.common.ReportToolBar;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurface;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceFirstGroupService;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.UUID;

import static io.github.therealmone.fireres.gui.synchronizer.impl.FirstThermocoupleGroupChartSynchronizer.MAX_MEAN_TEMPERATURE_TEXT;
import static io.github.therealmone.fireres.gui.synchronizer.impl.FirstThermocoupleGroupChartSynchronizer.MAX_THERMOCOUPLE_TEMPERATURE_TEXT;
import static java.util.Collections.singletonList;

@LoadableComponent("/component/unheated-surface/groups/first/firstGroup.fxml")
public class FirstGroup extends AbstractReportUpdaterComponent<TitledPane>
        implements UnheatedSurfaceReportContainer, Resettable {

    @FXML
    @Getter
    private VBox paramsVbox;

    @FXML
    private FirstGroupParams firstGroupParamsController;

    @FXML
    private FunctionParams functionParamsController;

    @FXML
    private FirstGroupChart firstGroupChartController;

    @FXML
    private BoundsShiftParams boundsShiftParamsController;

    @FXML
    private ReportToolBar toolBarController;

    @Inject
    private UnheatedSurfaceFirstGroupService unheatedSurfaceFirstGroupService;

    @Override
    protected void initialize() {
        initializeFunctionParams();
        initializeBoundsShiftParams();
    }

    private void initializeFunctionParams() {
        getFunctionParams().setInterpolationService(unheatedSurfaceFirstGroupService);

        getFunctionParams().setPropertiesMapper(props ->
                props.getReportPropertiesByClass(UnheatedSurfaceProperties.class)
                        .orElseThrow()
                        .getFirstGroup()
                        .getFunctionForm());

        getFunctionParams().setNodesToBlockOnUpdate(singletonList(paramsVbox));

        getFunctionParams().setInterpolationPointConstructor((time, value) -> new IntegerPoint(time, value.intValue()));
    }

    private void initializeBoundsShiftParams() {
        getBoundsShiftParams().addBoundShift(
                MAX_MEAN_TEMPERATURE_TEXT,
                singletonList(paramsVbox),
                properties -> ((UnheatedSurfaceProperties) properties).getFirstGroup().getBoundsShift().getMaxAllowedMeanTemperatureShift(),
                point -> unheatedSurfaceFirstGroupService.addMaxAllowedMeanTemperatureShift(getReport(), (IntegerPoint) point),
                point -> unheatedSurfaceFirstGroupService.removeMaxAllowedMeanTemperatureShift(getReport(), (IntegerPoint) point),
                (integer, number) -> new IntegerPoint(integer, number.intValue())
        );

        getBoundsShiftParams().addBoundShift(
                MAX_THERMOCOUPLE_TEMPERATURE_TEXT,
                singletonList(paramsVbox),
                properties -> ((UnheatedSurfaceProperties) properties).getFirstGroup().getBoundsShift().getMaxAllowedThermocoupleTemperatureShift(),
                point -> unheatedSurfaceFirstGroupService.addMaxAllowedThermocoupleTemperatureShift(getReport(), (IntegerPoint) point),
                point -> unheatedSurfaceFirstGroupService.removeMaxAllowedThermocoupleTemperatureShift(getReport(), (IntegerPoint) point),
                (integer, number) -> new IntegerPoint(integer, number.intValue())
        );
    }

    @Override
    public void refresh() {
        updateReport(() -> unheatedSurfaceFirstGroupService.refreshFirstGroup(getReport()));
    }

    @Override
    public void reset() {
        updateReport(() -> {
            getFirstGroupParams().reset();
            getFunctionParams().reset();
            getBoundsShiftParams().reset();
            unheatedSurfaceFirstGroupService.refreshFirstGroup(getReport());
        }, getParamsVbox());
    }

    @Override
    public Sample getSample() {
        return ((UnheatedSurface) getParent()).getSample();
    }

    @Override
    public UnheatedSurfaceReport getReport() {
        return ((UnheatedSurface) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return firstGroupChartController;
    }

    @Override
    protected UUID getReportId() {
        return getReport().getId();
    }

    public FirstGroupParams getFirstGroupParams() {
        return firstGroupParamsController;
    }

    public FunctionParams getFunctionParams() {
        return functionParamsController;
    }

    public BoundsShiftParams getBoundsShiftParams() {
        return boundsShiftParamsController;
    }

    public ReportToolBar getToolBar() {
        return toolBarController;
    }

}
