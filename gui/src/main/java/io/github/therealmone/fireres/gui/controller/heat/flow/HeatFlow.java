package io.github.therealmone.fireres.gui.controller.heat.flow;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excel.report.HeatFlowExcelReportsBuilder;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.component.DataViewer;
import io.github.therealmone.fireres.gui.configurer.report.HeatFlowParametersConfigurer;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.PresetChanger;
import io.github.therealmone.fireres.gui.controller.PresetContainer;
import io.github.therealmone.fireres.gui.controller.Refreshable;
import io.github.therealmone.fireres.gui.controller.ReportCreator;
import io.github.therealmone.fireres.gui.controller.ReportDataCollector;
import io.github.therealmone.fireres.gui.controller.ReportInclusionChanger;
import io.github.therealmone.fireres.gui.controller.Resettable;
import io.github.therealmone.fireres.gui.controller.common.BoundsShiftParams;
import io.github.therealmone.fireres.gui.controller.common.FunctionParams;
import io.github.therealmone.fireres.gui.controller.common.ReportToolBar;
import io.github.therealmone.fireres.gui.controller.common.SampleTab;
import io.github.therealmone.fireres.gui.model.ReportTask;
import io.github.therealmone.fireres.gui.preset.Preset;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import io.github.therealmone.fireres.heatflow.config.HeatFlowProperties;
import io.github.therealmone.fireres.heatflow.model.HeatFlowPoint;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.val;

import java.util.UUID;

import static io.github.therealmone.fireres.core.config.ReportType.HEAT_FLOW;
import static io.github.therealmone.fireres.gui.synchronizer.impl.HeatFlowChartSynchronizer.MAX_ALLOWED_FLOW_TEXT;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;
import static java.util.Collections.singletonList;

@LoadableComponent("/component/heat-flow/heatFlow.fxml")
public class HeatFlow extends AbstractReportUpdaterComponent<VBox>
        implements HeatFlowReportContainer, ReportInclusionChanger,
        ReportCreator, Resettable, ReportDataCollector, Refreshable, PresetChanger {

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

    @FXML
    private BoundsShiftParams boundsShiftParamsController;

    @FXML
    private ReportToolBar toolBarController;

    @Inject
    private HeatFlowExcelReportsBuilder excelReportsBuilder;

    @Inject
    private HeatFlowParametersConfigurer heatFlowParametersConfigurer;

    @Override
    public Sample getSample() {
        return ((SampleTab) getParent()).getSample();
    }

    @Override
    protected void initialize() {
        initializeFunctionParams();
        initializeBoundsShiftParams();
    }

    private void initializeFunctionParams() {
        getFunctionParams().setInterpolationService(heatFlowService);

        getFunctionParams().setPropertiesMapper(props ->
                props.getReportPropertiesByClass(HeatFlowProperties.class)
                        .orElseThrow()
                        .getFunctionForm());

        getFunctionParams().setNodesToBlockOnUpdate(singletonList(paramsVbox));

        getFunctionParams().setInterpolationPointConstructor((time, value) -> new HeatFlowPoint(time, value.doubleValue()));
    }

    private void initializeBoundsShiftParams() {
        getBoundsShiftParams().addBoundShift(
                MAX_ALLOWED_FLOW_TEXT,
                singletonList(paramsVbox),
                properties -> ((HeatFlowProperties) properties).getBoundsShift().getMaxAllowedFlowShift(),
                point -> heatFlowService.addMaxAllowedFlowShift(report, (HeatFlowPoint) point),
                point -> heatFlowService.removeMaxAllowedFlowShift(report, (HeatFlowPoint) point),
                (integer, number) -> new HeatFlowPoint(integer, number.doubleValue())
        );
    }

    @Override
    public void createReport() {
        val reportId = UUID.randomUUID();

        val task = ReportTask.builder()
                .updatingElementId(reportId)
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
    public void postConstruct() {
        heatFlowParametersConfigurer.config(this,
                ((PresetContainer) getParent()).getPreset());
    }

    @Override
    public void refresh() {
        createReport();
    }

    @Override
    public void reset() {
        updateReport(() -> {
            changePreset(((PresetContainer) getParent()).getPreset());
            refresh();
        }, getParamsVbox());
    }

    @Override
    public void changePreset(Preset preset) {
        heatFlowParametersConfigurer.config(this, preset);

        refresh();
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

    @Override
    public DataViewer getReportData() {
        val excelReports = excelReportsBuilder.build(
                generationProperties.getGeneral(), singletonList(report));

        if (excelReports.size() != 1) {
            throw new IllegalStateException();
        }

        return new DataViewer(excelReports.get(0));
    }

    public HeatFlowParams getHeatFlowParams() {
        return heatFlowParamsController;
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

    @Override
    public ChartContainer getChartContainer() {
        return heatFlowChartController;
    }

    @Override
    public UUID getUpdatingElementId() {
        return getReport().getId();
    }
}
