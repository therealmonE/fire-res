package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.common.BoundsShiftParams;
import io.github.therealmone.fireres.gui.controller.common.FunctionParams;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurface;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceThirdGroupService;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import static io.github.therealmone.fireres.gui.synchronizer.impl.ThirdThermocoupleGroupChartSynchronizer.MAX_THERMOCOUPLE_TEMPERATURE_TEXT;
import static java.util.Collections.singletonList;

@LoadableComponent("/component/unheated-surface/groups/third/thirdGroup.fxml")
public class ThirdGroup extends AbstractComponent<TitledPane> implements UnheatedSurfaceReportContainer {

    @FXML
    @Getter
    private VBox paramsVbox;

    @Inject
    private UnheatedSurfaceThirdGroupService unheatedSurfaceThirdGroupService;

    @FXML
    private ThirdGroupParams thirdGroupParamsController;

    @FXML
    private ThirdGroupChart thirdGroupChartController;

    @FXML
    private FunctionParams functionParamsController;

    @FXML
    private BoundsShiftParams boundsShiftParamsController;

    @Override
    public Sample getSample() {
        return ((UnheatedSurface) getParent()).getSample();
    }

    @Override
    protected void initialize() {
        initializeFunctionParams();
        initializeBoundsShiftParams();
    }

    private void initializeFunctionParams() {
        getFunctionParams().setInterpolationService(unheatedSurfaceThirdGroupService);

        getFunctionParams().setPropertiesMapper(props ->
                props.getReportPropertiesByClass(UnheatedSurfaceProperties.class)
                        .orElseThrow()
                        .getThirdGroup()
                        .getFunctionForm());

        getFunctionParams().setNodesToBlockOnUpdate(singletonList(paramsVbox));

        getFunctionParams().setInterpolationPointConstructor((time, value) -> new IntegerPoint(time, value.intValue()));
    }

    private void initializeBoundsShiftParams() {
        getBoundsShiftParams().addBoundShift(
                MAX_THERMOCOUPLE_TEMPERATURE_TEXT,
                singletonList(paramsVbox),
                point -> unheatedSurfaceThirdGroupService.addMaxAllowedTemperatureShift(getReport(), (IntegerPoint) point),
                point -> unheatedSurfaceThirdGroupService.removeMaxAllowedTemperatureShift(getReport(), (IntegerPoint) point),
                (integer, number) -> new IntegerPoint(integer, number.intValue())
        );
    }

    @Override
    public UnheatedSurfaceReport getReport() {
        return ((UnheatedSurface) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return thirdGroupChartController;
    }

    public ThirdGroupParams getThirdGroupParams() {
        return thirdGroupParamsController;
    }

    public FunctionParams getFunctionParams() {
        return functionParamsController;
    }

    public BoundsShiftParams getBoundsShiftParams() {
        return boundsShiftParamsController;
    }
}