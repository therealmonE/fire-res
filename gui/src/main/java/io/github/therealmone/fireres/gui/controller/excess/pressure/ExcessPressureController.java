package io.github.therealmone.fireres.gui.controller.excess.pressure;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportInclusionChanger;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.model.ReportTask;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.util.UUID;

import static io.github.therealmone.fireres.core.config.ReportType.EXCESS_PRESSURE;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;
import static java.util.Collections.singletonList;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExcessPressureController extends AbstractController implements ExcessPressureReportContainer, ReportInclusionChanger {

    @FXML
    private VBox excessPressureParamsVbox;

    private ExcessPressureReport report;

    @Inject
    private ExcessPressureService excessPressureService;

    @Inject
    private ChartsSynchronizationService chartsSynchronizationService;

    @FXML
    private ExcessPressureParamsController excessPressureParamsController;

    @FXML
    private ExcessPressureChartController excessPressureChartController;

    private SampleTabController sampleTabController;

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
        excessPressureParamsController.setExcessPressureController(this);
        excessPressureChartController.setExcessPressureController(this);
    }

    @Override
    public void postConstruct() {
        excessPressureParamsController.postConstruct();
    }

    @Override
    public ChartContainer getChartContainer() {
        return excessPressureChartController;
    }

    @Override
    public void createReport() {
        val reportId = UUID.randomUUID();

        val task = ReportTask.builder()
                .reportId(reportId)
                .chartContainers(singletonList(excessPressureChartController))
                .nodesToLock(singletonList(excessPressureParamsVbox))
                .action(() -> {
                    this.report = excessPressureService.createReport(reportId, getSample());

                    if (!generationProperties.getGeneral().getIncludedReports().contains(EXCESS_PRESSURE)) {
                        excludeReport();
                    }
                })
                .build();

        reportExecutorService.runTask(task);
    }

    @Override
    public void excludeReport() {
        getSample().removeReport(report);
        disableTab(sampleTabController.getExcessPressureTab());
        generationProperties.getGeneral().getIncludedReports().removeIf(EXCESS_PRESSURE::equals);
    }

    @Override
    public void includeReport() {
        getSample().putReport(report);
        enableTab(sampleTabController.getExcessPressureTab(), sampleTabController.getReportsTabPane());
        generationProperties.getGeneral().getIncludedReports().add(EXCESS_PRESSURE);
    }
}
