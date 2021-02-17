package io.github.therealmone.fireres.gui.controller.unheated.surface.groups.second;

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
public class SecondGroupChartController extends AbstractController implements UnheatedSurfaceReportContainer {

    @ParentController
    private SecondGroupController secondGroupController;

    @FXML
    private LineChart<Number, Number> secondGroupChart;

    @Override
    public UnheatedSurfaceReport getReport() {
        return secondGroupController.getReport();
    }

    @Override
    public Sample getSample() {
        return secondGroupController.getSample();
    }
}
