package io.github.therealmone.fireres.gui.controller.fire.mode;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excel.report.FireModeExcelReportsBuilder;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.component.DataViewer;
import io.github.therealmone.fireres.gui.configurer.report.FireModeParametersConfigurer;
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
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.val;

import java.util.UUID;

import static io.github.therealmone.fireres.core.config.ReportType.FIRE_MODE;
import static io.github.therealmone.fireres.gui.synchronizer.impl.FireModeChartSynchronizer.MAX_ALLOWED_TEMPERATURE_TEXT;
import static io.github.therealmone.fireres.gui.synchronizer.impl.FireModeChartSynchronizer.MIN_ALLOWED_TEMPERATURE_TEXT;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;
import static java.util.Collections.singletonList;

@LoadableComponent("/component/fire-mode/fireMode.fxml")
public class FireMode extends AbstractReportUpdaterComponent<VBox>
        implements FireModeReportContainer, ReportInclusionChanger,
        ReportCreator, Resettable, ReportDataCollector, Refreshable, PresetChanger {

    @FXML
    @Getter
    private VBox paramsVbox;

    @Getter
    private FireModeReport report;

    @Inject
    private FireModeService fireModeService;

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportExecutorService reportExecutorService;

    @FXML
    private FireModeParams fireModeParamsController;

    @FXML
    private FunctionParams functionParamsController;

    @FXML
    private FireModeChart fireModeChartController;

    @FXML
    private BoundsShiftParams boundsShiftParamsController;

    @FXML
    private ReportToolBar toolBarController;

    @Inject
    private FireModeExcelReportsBuilder excelReportsBuilder;

    @Inject
    private FireModeParametersConfigurer fireModeParametersConfigurer;

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
        getFunctionParams().setInterpolationService(fireModeService);

        getFunctionParams().setPropertiesMapper(props ->
                props.getReportPropertiesByClass(FireModeProperties.class)
                        .orElseThrow()
                        .getFunctionForm());

        getFunctionParams().setNodesToBlockOnUpdate(singletonList(paramsVbox));

        getFunctionParams().setInterpolationPointConstructor((time, value) -> new IntegerPoint(time, value.intValue()));
    }

    private void initializeBoundsShiftParams() {
        getBoundsShiftParams().addBoundShift(
                MAX_ALLOWED_TEMPERATURE_TEXT,
                singletonList(paramsVbox),
                properties -> ((FireModeProperties) properties).getBoundsShift().getMaxAllowedTemperatureShift(),
                point -> fireModeService.addMaxAllowedTemperatureShift(report, (IntegerPoint) point),
                point -> fireModeService.removeMaxAllowedTemperatureShift(report, (IntegerPoint) point),
                (integer, number) -> new IntegerPoint(integer, number.intValue())
        );

        getBoundsShiftParams().addBoundShift(
                MIN_ALLOWED_TEMPERATURE_TEXT,
                singletonList(paramsVbox),
                properties -> ((FireModeProperties) properties).getBoundsShift().getMinAllowedTemperatureShift(),
                point -> fireModeService.addMinAllowedTemperatureShift(report, (IntegerPoint) point),
                point -> fireModeService.removeMinAllowedTemperatureShift(report, (IntegerPoint) point),
                (integer, number) -> new IntegerPoint(integer, number.intValue())
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
                    this.report = fireModeService.createReport(reportId, getSample());

                    if (!generationProperties.getGeneral().getIncludedReports().contains(FIRE_MODE)) {
                        excludeReport();
                    }
                })
                .build();

        reportExecutorService.runTask(task);
    }

    @Override
    public void postConstruct() {
        fireModeParametersConfigurer.config(this,
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
        fireModeParametersConfigurer.config(this, preset);

        refresh();
    }

    @Override
    public void excludeReport() {
        getSample().removeReport(report);
        disableTab(((SampleTab) getParent()).getFireModeTab());
        generationProperties.getGeneral().getIncludedReports().removeIf(FIRE_MODE::equals);
    }

    @Override
    public void includeReport() {
        val parent = ((SampleTab) getParent());

        getSample().putReport(report);
        enableTab(parent.getFireModeTab(), parent.getReportsTabPane());
        generationProperties.getGeneral().getIncludedReports().add(FIRE_MODE);
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

    public FireModeParams getFireModeParams() {
        return fireModeParamsController;
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
        return fireModeChartController;
    }

    @Override
    public UUID getUpdatingElementId() {
        return getReport().getId();
    }
}
