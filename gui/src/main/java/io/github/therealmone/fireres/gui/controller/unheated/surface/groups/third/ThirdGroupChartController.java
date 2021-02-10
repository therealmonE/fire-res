package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third;

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
public class ThirdGroupChartController extends AbstractController implements ReportContainer {

    @ParentController
    private ThirdGroupController thirdGroupController;

    @FXML
    private LineChart<Number, Number> thirdGroupChart;

    @Override
    public Report getReport() {
        return thirdGroupController.getReport();
    }

    @Override
    public Sample getSample() {
        return thirdGroupController.getSample();
    }
}
