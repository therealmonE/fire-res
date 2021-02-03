package io.github.therealmone.fireres.gui.controller.fire.mode;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.service.ChartsSynchronizationService;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FireModePaneController extends AbstractController implements ReportContainer {

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

    @Override
    public Sample getSample() {
        return sampleTabController.getSample();
    }

    @Override
    protected void initialize() {
        fireModeParamsController.setFireModePaneController(this);
        functionParamsController.setParentController(this);
        fireModeChartController.setFireModePaneController(this);
    }

    @Override
    public void postConstruct() {
        fireModeParamsController.postConstruct();
        functionParamsController.postConstruct();
        fireModeChartController.postConstruct();

        this.report = fireModeService.createReport(getSample());
        chartsSynchronizationService.syncFireModeChart(fireModeChartController.getFireModeChart(), report);
    }

    @Override
    public Report getReport() {
        return report;
    }
}
