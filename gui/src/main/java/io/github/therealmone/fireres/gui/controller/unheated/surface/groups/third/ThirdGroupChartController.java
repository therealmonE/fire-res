package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third;

import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfaceReportContainer;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ThirdGroupChartController extends AbstractController implements UnheatedSurfaceReportContainer {

    @ParentController
    private ThirdGroupController thirdGroupController;

    @FXML
    private LineChart<Number, Number> thirdGroupChart;

    @Override
    public UnheatedSurfaceReport getReport() {
        return thirdGroupController.getReport();
    }

    @Override
    public Sample getSample() {
        return thirdGroupController.getSample();
    }
}
