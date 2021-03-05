package io.github.therealmone.fireres.gui.controller.heat.flow;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportInclusionChanger;
import io.github.therealmone.fireres.gui.controller.common.FunctionParams;
import io.github.therealmone.fireres.gui.controller.common.SampleTab;
import io.github.therealmone.fireres.gui.model.ReportTask;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPoint;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.val;

import java.util.UUID;

import static io.github.therealmone.fireres.core.config.ReportType.HEAT_FLOW;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;
import static java.util.Collections.singletonList;

@LoadableComponent("/component/heat-flow/heatFlow.fxml")
public class HeatFlow extends AbstractComponent<HBox>
        implements HeatFlowReportContainer, ReportInclusionChanger {

    @FXML
    @Getter
    private VBox paramsVbox;

    @Getter
    private HeatFlowReport report;

    @Inject
    private HeatFlowService heatFlowService;

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportExecutorService reportExecutorService;

    @FXML
    private HeatFlowParams heatFlowParamsController;

    @FXML
    private HeatFlowChart heatFlowChartController;

    @FXML
    private FunctionParams functionParamsController;

    @Override
    public Sample getSample() {
        return ((SampleTab) getParent()).getSample();
    }

    @Override
    protected void initialize() {
        getFunctionParams().setInterpolationService(heatFlowService);

        getFunctionParams().setPropertiesMapper(props ->
                props.getReportPropertiesByClass(HeatFlowProperties.class)
                        .orElseThrow()
                        .getFunctionForm());

        getFunctionParams().setNodesToBlockOnUpdate(singletonList(paramsVbox));

        getFunctionParams().setInterpolationPointConstructor((time, value) -> new HeatFlowPoint(time, value.doubleValue()));
    }

    @Override
    public ChartContainer getChartContainer() {
        return heatFlowChartController;
    }

    @Override
    public void createReport() {
        val reportId = UUID.randomUUID();

        val task = ReportTask.builder()
                .reportId(reportId)
                .chartContainers(singletonList(getChartContainer()))
                .nodesToLock(singletonList(paramsVbox))
                .action(() -> {
                    this.report = heatFlowService.createReport(reportId, getSample());

                    if (!generationProperties.getGeneral().getIncludedReports().contains(HEAT_FLOW)) {
                        excludeReport();
                    }
                })
                .build();

        reportExecutorService.runTask(task);
    }

    @Override
    public void excludeReport() {
        getSample().removeReport(report);
        disableTab(((SampleTab) getParent()).getHeatFlowTab());
        generationProperties.getGeneral().getIncludedReports().removeIf(HEAT_FLOW::equals);
    }

    @Override
    public void includeReport() {
        val parent = ((SampleTab) getParent());

        getSample().putReport(report);
        enableTab(parent.getHeatFlowTab(), parent.getReportsTabPane());
        generationProperties.getGeneral().getIncludedReports().add(HEAT_FLOW);
    }

    public HeatFlowParams getHeatFlowParams() {
        return heatFlowParamsController;
    }

    public FunctionParams getFunctionParams() {
        return functionParamsController;
    }

}
