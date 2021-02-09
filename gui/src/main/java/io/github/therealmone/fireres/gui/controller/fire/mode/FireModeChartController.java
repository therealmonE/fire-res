package io.github.therealmone.fireres.gui.controller.fire.mode;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FireModeChartController extends AbstractController implements ReportContainer {

    @ParentController
    private FireModeController fireModeController;

    @FXML
    private LineChart<Number, Number> fireModeChart;

    @Override
    protected void initialize() {
    }

    @Override
    public Report getReport() {
        return fireModeController.getReport();
    }

    @Override
    public Sample getSample() {
        return fireModeController.getSample();
    }

}
