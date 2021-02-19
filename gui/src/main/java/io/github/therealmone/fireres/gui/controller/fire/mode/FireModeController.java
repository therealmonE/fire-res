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
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static io.github.therealmone.fireres.core.config.ReportType.FIRE_MODE;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;

@EqualsAndHashCode(callSuper = true)
@Data
public class FireModeController extends AbstractController implements FireModeReportContainer, ReportInclusionChanger {

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
        this.report = fireModeService.createReport(getSample());

        if (!generationProperties.getGeneral().getIncludedReports().contains(FIRE_MODE)) {
            excludeReport();
        }

        chartsSynchronizationService.syncFireModeChart(fireModeChartController.getFireModeChart(), report);
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
