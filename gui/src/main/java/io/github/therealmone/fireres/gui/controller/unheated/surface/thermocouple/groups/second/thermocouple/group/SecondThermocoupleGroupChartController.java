package io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.second.thermocouple.group;

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
public class SecondThermocoupleGroupChartController extends AbstractController implements ReportContainer {

    @ParentController
    private SecondThermocoupleGroupPaneController secondThermocoupleGroupPaneController;

    @FXML
    private LineChart<Number, Number> secondThermocoupleGroupChart;

    @Override
    public Report getReport() {
        return secondThermocoupleGroupPaneController.getReport();
    }

    @Override
    public Sample getSample() {
        return secondThermocoupleGroupPaneController.getSample();
    }
}
