package io.github.therealmone.fireres.gui.controller.fire.mode;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportInclusionChanger;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.model.ReportTask;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.util.UUID;

import static io.github.therealmone.fireres.core.config.ReportType.FIRE_MODE;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;
import static java.util.Collections.singletonList;

@EqualsAndHashCode(callSuper = true)
@Data
public class FireModeController extends AbstractController implements FireModeReportContainer, ReportInclusionChanger {

    @FXML
    private VBox fireModeParamsVbox;

    private FireModeReport report;

    @Inject
    private FireModeService fireModeService;

    private SampleTabController sampleTabController;

    @FXML
    private FireModeParamsController fireModeParamsController;

    @FXML
    private FunctionParamsController functionParamsController;

    @FXML
    private FireModeChartController fireModeChartController;

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
        fireModeParamsController.setFireModeController(this);
        fireModeChartController.setFireModeController(this);

        functionParamsController.setParentController(this);
        functionParamsController.setInterpolationService(fireModeService);

        functionParamsController.setPropertiesMapper(props ->
                props.getReportPropertiesByClass(FireModeProperties.class).orElseThrow());

        functionParamsController.setNodesToBlockOnUpdate(singletonList(fireModeParamsVbox));

        functionParamsController.setInterpolationPointConstructor((time, value) -> new IntegerPoint(time, value.intValue()));
    }

    @Override
    public void postConstruct() {
        fireModeParamsController.postConstruct();
        functionParamsController.postConstruct();
        fireModeChartController.postConstruct();
    }

    @Override
    public ChartContainer getChartContainer() {
        return fireModeChartController;
    }

    @Override
    public void createReport() {
        val reportId = UUID.randomUUID();

        val task = ReportTask.builder()
                .reportId(reportId)
                .chartContainers(singletonList(fireModeChartController))
                .nodesToLock(singletonList(fireModeParamsVbox))
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
    public void excludeReport() {
        getSample().removeReport(report);
        disableTab(sampleTabController.getFireModeTab());
        generationProperties.getGeneral().getIncludedReports().removeIf(FIRE_MODE::equals);
    }

    @Override
    public void includeReport() {
        getSample().putReport(report);
        enableTab(sampleTabController.getFireModeTab(), sampleTabController.getReportsTabPane());
        generationProperties.getGeneral().getIncludedReports().add(FIRE_MODE);
    }
}
