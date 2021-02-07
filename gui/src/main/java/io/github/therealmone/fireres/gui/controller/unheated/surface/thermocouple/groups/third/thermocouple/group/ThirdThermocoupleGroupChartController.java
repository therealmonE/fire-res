package io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.third.thermocouple.group;

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
public class ThirdThermocoupleGroupChartController extends AbstractController implements ReportContainer {

    @ParentController
    private ThirdThermocoupleGroupPaneController thirdThermocoupleGroupPaneController;

    @FXML
    private LineChart<Number, Number> thirdThermocoupleGroupChart;

    @Override
    public Report getReport() {
        return thirdThermocoupleGroupPaneController.getReport();
    }

    @Override
    public Sample getSample() {
        return thirdThermocoupleGroupPaneController.getSample();
    }
}
