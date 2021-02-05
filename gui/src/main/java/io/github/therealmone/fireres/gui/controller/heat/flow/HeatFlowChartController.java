package io.github.therealmone.fireres.gui.controller.heat.flow;

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
public class HeatFlowChartController extends AbstractController implements ReportContainer {

    @FXML
    private LineChart<Number, Number> heatFlowChart;

    @ParentController
    private HeatFlowPaneController heatFlowPaneController;

    @Override
    public Report getReport() {
        return heatFlowPaneController.getReport();
    }

    @Override
    public Sample getSample() {
        return heatFlowPaneController.getSample();
    }

}
