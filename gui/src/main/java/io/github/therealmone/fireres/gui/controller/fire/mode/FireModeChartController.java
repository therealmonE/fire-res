package io.github.therealmone.fireres.gui.controller.fire.mode;

import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FireModeChartController extends AbstractController implements FireModeReportContainer {

    @ParentController
    private FireModeController fireModeController;

    @FXML
    private LineChart<Number, Number> fireModeChart;

    @Override
    protected void initialize() {
    }

    @Override
    public FireModeReport getReport() {
        return fireModeController.getReport();
    }

    @Override
    public Sample getSample() {
        return fireModeController.getSample();
    }

}
