package io.github.therealmone.fireres.gui.controller.excess.pressure;

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
public class ExcessPressureChartController extends AbstractController implements ReportContainer {

    @FXML
    private LineChart<Number, Number> excessPressureChart;

    @ParentController
    private ExcessPressureController excessPressureController;

    @Override
    public Report getReport() {
        return excessPressureController.getReport();
    }

    @Override
    public Sample getSample() {
        return excessPressureController.getSample();
    }
}
