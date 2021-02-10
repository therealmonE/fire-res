package io.github.therealmone.fireres.gui.controller.fire.mode;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
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
public class FireModeController extends AbstractController implements ReportContainer, ReportInclusionChanger {

    private FireModeReport report;

    @Inject
    private FireModeService fireModeService;

    @ParentController
    private SampleTabController sampleTabController;

    @FXML
    @ChildController
    private FireModeParamsController fireModeParamsController;

    @FXML
    @ChildController
    private FunctionParamsController functionParamsController;

    @FXML
    @ChildController
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
        functionParamsController.setPropertiesMapper(SampleProperties::getFireMode);
        functionParamsController.setPostReportUpdateAction(() ->
                chartsSynchronizationService.syncFireModeChart(fireModeChartController.getFireModeChart(), report));
    }

    @Override
    public void postConstruct() {
        fireModeParamsController.postConstruct();
        functionParamsController.postConstruct();
        fireModeChartController.postConstruct();
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
    public Report getReport() {
        return report;
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
