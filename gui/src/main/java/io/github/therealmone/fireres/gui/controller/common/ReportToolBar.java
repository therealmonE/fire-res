package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.ReportDataCollector;
import io.github.therealmone.fireres.gui.controller.ReportUpdater;
import io.github.therealmone.fireres.gui.controller.Resettable;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.modal.DataViewerModalWindow;
import io.github.therealmone.fireres.gui.service.AlertService;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import io.github.therealmone.fireres.gui.service.ReportUpdateListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import lombok.val;

import java.util.UUID;

@SuppressWarnings("rawtypes")
public class ReportToolBar extends AbstractComponent<ToolBar>
        implements SampleContainer, ReportContainer {

    @FXML
    private ToggleButton legendVisibilityButton;

    @Inject
    private AlertService alertService;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @Inject
    private ReportExecutorService reportExecutorService;

    @FXML
    public void refreshReport() {
        ((ReportContainer) getParent()).refresh();
    }

    @FXML
    public void resetSettings() {
        alertService.showConfirmation(
                "Вы уверены, что хотите сбросить все параметры?",
                () -> ((Resettable) getParent()).reset());
    }

    @FXML
    public void changeLegendVisibility() {
        if (!legendVisibilityButton.isSelected()) {
            legendVisibilityButton.getTooltip().setText("Показать легенду");
            getChartContainer().getChart().setLegendVisible(false);
        } else {
            legendVisibilityButton.getTooltip().setText("Скрыть легенду");
            getChartContainer().getChart().setLegendVisible(true);
        }
    }

    @FXML
    public void showReportData() {
        val modalWindow = fxmlLoadService.loadComponent(DataViewerModalWindow.class, this);

        updateDataViewer(modalWindow);
        modalWindow.getWindow().show();

        val listener = new ReportUpdateListener() {
            @Override
            public void postUpdate(UUID elementId) {
                if (elementId.equals(((ReportUpdater) getParent()).getUpdatingElementId())) {
                    Platform.runLater(() -> updateDataViewer(modalWindow));
                }
            }
        };

        reportExecutorService.addListener(listener);
        modalWindow.getWindow().setOnCloseRequest(event -> reportExecutorService.removeListener(listener));
    }

    private void updateDataViewer(DataViewerModalWindow modalWindow) {
        val dataViewer = ((ReportDataCollector) getParent()).getReportData();

        modalWindow.setDataViewer(dataViewer);
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
    public Sample getSample() {
        return ((ReportContainer) getParent()).getSample();
    }

}
