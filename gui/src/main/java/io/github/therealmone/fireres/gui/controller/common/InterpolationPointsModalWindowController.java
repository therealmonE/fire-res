package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.service.AlertService;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.util.Collections;

@SuppressWarnings("unchecked")
@EqualsAndHashCode(callSuper = true)
@Data
public class InterpolationPointsModalWindowController extends AbstractController implements ReportContainer {

    @ParentController
    private FunctionParamsController functionParamsController;

    @FXML
    private Spinner<Integer> interpolationPointTimeSpinner;

    @FXML
    private Spinner<Integer> interpolationPointValueSpinner;

    private Stage modalWindow;

    @Inject
    private AlertService alertService;

    public void addInterpolationPoint() {
        val newPoint = new InterpolationPoint(interpolationPointTimeSpinner.getValue(), interpolationPointValueSpinner.getValue());

        try {
            functionParamsController.getInterpolationService().addInterpolationPoints(getReport(), Collections.singletonList(newPoint));
        } catch (Exception e) {
            alertService.showError("Невозможно добавить данную точку");
            throw e;
        }

        functionParamsController.getInterpolationPointsTableView().getItems().add(newPoint);
        functionParamsController.getPostReportUpdateAction().run();
    }

    public void closeWindow() {
        modalWindow.close();
    }

    @Override
    public Report getReport() {
        return functionParamsController.getReport();
    }

    @Override
    public Sample getSample() {
        return functionParamsController.getSample();
    }
}
