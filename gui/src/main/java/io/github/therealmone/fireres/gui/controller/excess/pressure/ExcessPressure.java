package io.github.therealmone.fireres.gui.controller.excess.pressure;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.DoublePoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportInclusionChanger;
import io.github.therealmone.fireres.gui.controller.common.BoundsShiftParams;
import io.github.therealmone.fireres.gui.controller.common.SampleTab;
import io.github.therealmone.fireres.gui.model.ReportTask;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.val;

import java.util.UUID;

import static io.github.therealmone.fireres.core.config.ReportType.EXCESS_PRESSURE;
import static io.github.therealmone.fireres.gui.synchronizer.impl.ExcessPressureChartSynchronizer.MAX_ALLOWED_PRESSURE;
import static io.github.therealmone.fireres.gui.synchronizer.impl.ExcessPressureChartSynchronizer.MIN_ALLOWED_PRESSURE;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;
import static java.util.Collections.singletonList;

@LoadableComponent("/component/excess-pressure/excessPressure.fxml")
public class ExcessPressure extends AbstractComponent<HBox>
        implements ExcessPressureReportContainer, ReportInclusionChanger {

    @FXML
    @Getter
    private VBox paramsVbox;

    @Getter
    private ExcessPressureReport report;

    @Inject
    private ExcessPressureService excessPressureService;

    @FXML
    private ExcessPressureParams excessPressureParamsController;

    @FXML
    private ExcessPressureChart excessPressureChartController;

    @FXML
    private BoundsShiftParams boundsShiftParamsController;

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportExecutorService reportExecutorService;

    @Override
    public Sample getSample() {
        return ((SampleTab) getParent()).getSample();
    }

    @Override
    protected void initialize() {
        getBoundsShiftParams().addBoundShift(
                MAX_ALLOWED_PRESSURE,
                singletonList(paramsVbox),
                point -> excessPressureService.addMaxAllowedPressureShift(report, (DoublePoint) point),
                point -> excessPressureService.removeMaxAllowedPressureShift(report, (DoublePoint) point),
                (integer, number) -> new DoublePoint(integer, number.doubleValue())
        );

        getBoundsShiftParams().addBoundShift(
                MIN_ALLOWED_PRESSURE,
                singletonList(paramsVbox),
                point -> excessPressureService.addMinAllowedPressureShift(report, (DoublePoint) point),
                point -> excessPressureService.removeMinAllowedPressureShift(report, (DoublePoint) point),
                (integer, number) -> new DoublePoint(integer, number.doubleValue())
        );
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
                .chartContainers(singletonList(getChartContainer()))
                .nodesToLock(singletonList(paramsVbox))
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
        disableTab(((SampleTab) getParent()).getExcessPressureTab());
        generationProperties.getGeneral().getIncludedReports().removeIf(EXCESS_PRESSURE::equals);
    }

    @Override
    public void includeReport() {
        val parent = (SampleTab) getParent();

        getSample().putReport(report);
        enableTab(parent.getExcessPressureTab(), parent.getReportsTabPane());
        generationProperties.getGeneral().getIncludedReports().add(EXCESS_PRESSURE);
    }

    public BoundsShiftParams getBoundsShiftParams() {
        return boundsShiftParamsController;
    }

}
