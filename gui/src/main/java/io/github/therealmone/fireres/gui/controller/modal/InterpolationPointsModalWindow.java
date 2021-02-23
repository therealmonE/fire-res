package io.github.therealmone.fireres.gui.controller.modal;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.annotation.ModalWindow;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.common.FunctionParams;
import io.github.therealmone.fireres.gui.service.AlertService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.val;

import java.util.UUID;

@SuppressWarnings("unchecked")
@LoadableComponent("/component/modal/interpolationPointsModalWindow.fxml")
@ModalWindow(title = "Добавление точек интерполяции")
public class InterpolationPointsModalWindow extends AbstractReportUpdaterComponent<Pane>
        implements ReportContainer {

    @FXML
    @Getter
    private Spinner<Integer> time;

    @FXML
    @Getter
    private Spinner<Number> value;

    @Inject
    private AlertService alertService;

    @ModalWindow.Window
    @Getter
    private Stage window;

    @FXML
    public void addInterpolationPoint() {
        val parent = ((FunctionParams) getParent());

        updateReport(() -> {
            val newPoint = parent.getInterpolationPointConstructor()
                    .apply(time.getValue(), value.getValue());

            try {
                parent.getInterpolationService().addInterpolationPoint(getReport(), newPoint);
            } catch (Exception e) {
                Platform.runLater(() -> alertService.showError("Невозможно добавить данную точку"));
                throw e;
            }

            Platform.runLater(() -> parent.getInterpolationPoints().getItems().add(newPoint));
        }, parent.getNodesToBlockOnUpdate());
    }

    @FXML
    public void closeWindow() {
        window.close();
    }

    @Override
    public Report getReport() {
        return ((FunctionParams) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return ((FunctionParams) getParent()).getChartContainer();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getId();
    }

    @Override
    public Sample getSample() {
        return ((FunctionParams) getParent()).getSample();
    }

}
