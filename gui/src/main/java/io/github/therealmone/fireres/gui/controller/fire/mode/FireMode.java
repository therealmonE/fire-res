package io.github.therealmone.fireres.gui.controller.fire.mode;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportInclusionChanger;
import io.github.therealmone.fireres.gui.controller.common.FunctionParams;
import io.github.therealmone.fireres.gui.controller.common.SampleTab;
import io.github.therealmone.fireres.gui.model.ReportTask;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.val;

import java.util.UUID;

import static io.github.therealmone.fireres.core.config.ReportType.FIRE_MODE;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;
import static java.util.Collections.singletonList;

@LoadableComponent("/component/fire-mode/fireMode.fxml")
public class FireMode extends AbstractComponent<HBox>
        implements FireModeReportContainer, ReportInclusionChanger {

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

    @Override
    public Sample getSample() {
        return ((SampleTab) getParent()).getSample();
    }

    @Override
    protected void initialize() {
        getFunctionParams().setInterpolationService(fireModeService);

        getFunctionParams().setPropertiesMapper(props ->
                props.getReportPropertiesByClass(FireModeProperties.class).orElseThrow());

        getFunctionParams().setNodesToBlockOnUpdate(singletonList(paramsVbox));

        getFunctionParams().setInterpolationPointConstructor((time, value) -> new IntegerPoint(time, value.intValue()));
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

    public FireModeParams getFireModeParams() {
        return fireModeParamsController;
    }

    public FunctionParams getFunctionParams() {
        return functionParamsController;
    }

}
