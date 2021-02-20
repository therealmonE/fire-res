package io.github.therealmone.fireres.gui.controller.heat.flow;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportInclusionChanger;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.model.ReportTask;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPoint;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.util.UUID;

import static io.github.therealmone.fireres.core.config.ReportType.HEAT_FLOW;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;
import static java.util.Collections.singletonList;

@EqualsAndHashCode(callSuper = true)
@Data
public class HeatFlowController extends AbstractController implements HeatFlowReportContainer, ReportInclusionChanger {

    @FXML
    private VBox heatFlowParamsVbox;

    private HeatFlowReport report;

    @FXML
    private HeatFlowParamsController heatFlowParamsController;

    @FXML
    private HeatFlowChartController heatFlowChartController;

    @FXML
    private FunctionParamsController functionParamsController;

    private SampleTabController sampleTabController;

    @Inject
    private HeatFlowService heatFlowService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportExecutorService reportExecutorService;

    @Override
    public Sample getSample() {
        return sampleTabController.getSample();
    }

    @Override
    protected void initialize() {
        heatFlowParamsController.setHeatFlowController(this);
        heatFlowChartController.setHeatFlowController(this);

        functionParamsController.setParentController(this);
        functionParamsController.setInterpolationService(heatFlowService);

        functionParamsController.setPropertiesMapper(props ->
                props.getReportPropertiesByClass(HeatFlowProperties.class).orElseThrow());

        functionParamsController.setNodesToBlockOnUpdate(singletonList(heatFlowParamsVbox));

        functionParamsController.setInterpolationPointConstructor((time, value) -> new HeatFlowPoint(time, value.doubleValue()));
    }

    @Override
    public void postConstruct() {
        heatFlowParamsController.postConstruct();
        functionParamsController.postConstruct();
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
                .chartContainers(singletonList(heatFlowChartController))
                .nodesToLock(singletonList(heatFlowParamsVbox))
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
        disableTab(sampleTabController.getHeatFlowTab());
        generationProperties.getGeneral().getIncludedReports().removeIf(HEAT_FLOW::equals);
    }

    @Override
    public void includeReport() {
        getSample().putReport(report);
        enableTab(sampleTabController.getHeatFlowTab(), sampleTabController.getReportsTabPane());
        generationProperties.getGeneral().getIncludedReports().add(HEAT_FLOW);
    }
}
