package io.github.therealmone.fireres.gui.controller.modal;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.annotation.ModalWindow;
import io.github.therealmone.fireres.gui.component.ContextlessSpinner;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.ReportUpdater;
import io.github.therealmone.fireres.gui.controller.common.BoundShift;
import io.github.therealmone.fireres.gui.exception.NotNotifiableException;
import io.github.therealmone.fireres.gui.service.AlertService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.UUID;

@SuppressWarnings("rawtypes")
@LoadableComponent("/component/modal/boundsShiftModalWindow.fxml")
@ModalWindow(title = "Смещение границы")
@Slf4j
public class BoundShiftModalWindow extends AbstractReportUpdaterComponent<Pane>
        implements ReportContainer {

    @FXML
    private ContextlessSpinner<Integer> time;

    @FXML
    private ContextlessSpinner<Number> value;

    @ModalWindow.Window
    @Getter
    private Stage window;

    @Inject
    private AlertService alertService;

    @FXML
    public void addShift() {
        val parent = ((BoundShift) getParent());

        updateReport(() -> {
            val newPoint = parent.getShiftPointConstructor().apply(time.getValue(), value.getValue());

            try {
                parent.getShiftAddedConsumer().accept(newPoint);
            } catch (Exception e) {
                Platform.runLater(() -> alertService.showError("Невозможно сгенерировать график с данным смещением"));
                throw new NotNotifiableException(e);
            }

            Platform.runLater(() -> parent.getBoundShiftTable().getItems().add(newPoint));
        }, parent.getNodesToBlockOnUpdate());
    }

    @FXML
    public void closeWindow() {
        window.close();
    }

    @Override
    public Report getReport() {
        return ((ReportContainer) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return ((ReportContainer) getParent()).getChartContainer();
    }

    @Override
    public UUID getUpdatingElementId() {
        return ((ReportUpdater) getParent()).getUpdatingElementId();
    }

    @Override
    public Sample getSample() {
        return ((ReportContainer) getParent()).getSample();
    }
}
