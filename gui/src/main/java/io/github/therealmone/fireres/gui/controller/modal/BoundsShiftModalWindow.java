package io.github.therealmone.fireres.gui.controller.modal;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.annotation.ModalWindow;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.UUID;

@LoadableComponent("/component/modal/boundsShiftModalWindow.fxml")
@ModalWindow(title = "Смещение границы")
public class BoundsShiftModalWindow extends AbstractReportUpdaterComponent<Pane>
        implements ReportContainer {

    @ModalWindow.Window
    @Getter
    private Stage window;

    @Override
    public Report getReport() {
        return null;
    }

    @Override
    public ChartContainer getChartContainer() {
        return null;
    }

    @Override
    protected UUID getReportId() {
        return null;
    }

    @Override
    public Sample getSample() {
        return null;
    }
}
