package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.second;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.common.FunctionParams;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurface;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceSecondGroupService;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import static java.util.Collections.singletonList;

@LoadableComponent("/component/unheated-surface/groups/second/secondGroup.fxml")
public class SecondGroup extends AbstractComponent<TitledPane> implements UnheatedSurfaceReportContainer {

    @FXML
    @Getter
    private VBox paramsVbox;

    @Inject
    private UnheatedSurfaceSecondGroupService unheatedSurfaceSecondGroupService;

    @FXML
    private SecondGroupParams secondGroupParamsController;

    @FXML
    private FunctionParams functionParamsController;

    @FXML
    private SecondGroupChart secondGroupChartController;

    @Override
    protected void initialize() {
        getFunctionParams().setInterpolationService(unheatedSurfaceSecondGroupService);

        getFunctionParams().setPropertiesMapper(props ->
                props.getReportPropertiesByClass(UnheatedSurfaceProperties.class).orElseThrow().getSecondGroup());

        getFunctionParams().setNodesToBlockOnUpdate(singletonList(paramsVbox));

        getFunctionParams().setInterpolationPointConstructor((time, value) -> new IntegerPoint(time, value.intValue()));
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
        return secondGroupChartController;
    }

    public SecondGroupParams getSecondGroupParams() {
        return secondGroupParamsController;
    }

    public FunctionParams getFunctionParams() {
        return functionParamsController;
    }
}
