package io.github.therealmone.fireres.gui.controller.heat.flow;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportInclusionChanger;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static io.github.therealmone.fireres.core.config.ReportType.HEAT_FLOW;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;

@EqualsAndHashCode(callSuper = true)
@Data
public class HeatFlowController extends AbstractController implements HeatFlowReportContainer, ReportInclusionChanger {

    private HeatFlowReport report;

    @FXML
    @ChildController
    private HeatFlowParamsController heatFlowParamsController;

    @FXML
    @ChildController
    private HeatFlowChartController heatFlowChartController;

    @FXML
    @ChildController
    private FunctionParamsController functionParamsController;

    @ParentController
    private SampleTabController sampleTabController;

    @Inject
    private HeatFlowService heatFlowService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @Inject
    private GenerationProperties generationProperties;

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

        functionParamsController.setPostReportUpdateAction(() ->
                chartsSynchronizationService.syncHeatFlowChart(heatFlowChartController.getHeatFlowChart(), report));
    }

    @Override
    public void postConstruct() {
        heatFlowParamsController.postConstruct();
        functionParamsController.postConstruct();
    }

    @Override
    public void createReport() {
        this.report = heatFlowService.createReport(getSample());

        if (!generationProperties.getGeneral().getIncludedReports().contains(HEAT_FLOW)) {
            excludeReport();
        }

        chartsSynchronizationService.syncHeatFlowChart(heatFlowChartController.getHeatFlowChart(), report);
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
