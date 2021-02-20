package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.service.AlertService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.util.UUID;

@SuppressWarnings("unchecked")
@EqualsAndHashCode(callSuper = true)
@Data
public class InterpolationPointsModalWindowController extends AbstractReportUpdaterController implements ReportContainer {

    private FunctionParamsController functionParamsController;

    @FXML
    private Spinner<Integer> interpolationPointTimeSpinner;

    @FXML
    private Spinner<Number> interpolationPointValueSpinner;

    private Stage modalWindow;

    @Inject
    private AlertService alertService;

    @FXML
    public void addInterpolationPoint() {
        updateReport(() -> {
            val newPoint = functionParamsController.getInterpolationPointConstructor()
                    .apply(interpolationPointTimeSpinner.getValue(), interpolationPointValueSpinner.getValue());

            try {
                functionParamsController.getInterpolationService().addInterpolationPoint(getReport(), newPoint);
            } catch (Exception e) {
                Platform.runLater(() -> alertService.showError("Невозможно добавить данную точку"));
                throw e;
            }

            Platform.runLater(() -> functionParamsController.getInterpolationPointsTableView().getItems().add(newPoint));
        }, functionParamsController.getNodesToBlockOnUpdate());
    }

    @FXML
    public void closeWindow() {
        modalWindow.close();
    }

    @Override
    public Report getReport() {
        return functionParamsController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return functionParamsController.getChartContainer();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getId();
    }

    @Override
    public Sample getSample() {
        return functionParamsController.getSample();
    }
}
