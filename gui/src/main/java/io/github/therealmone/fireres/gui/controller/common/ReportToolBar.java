package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.Resettable;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.service.AlertService;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;

@SuppressWarnings("rawtypes")
public class ReportToolBar extends AbstractComponent<ToolBar>
        implements SampleContainer, ReportContainer {

    @FXML
    private ToggleButton legendVisibilityButton;

    @Inject
    private AlertService alertService;

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
        if (legendVisibilityButton.isSelected()) {
            legendVisibilityButton.getTooltip().setText("Показать легенду");
            getChartContainer().getChart().setLegendVisible(false);
        } else {
            legendVisibilityButton.getTooltip().setText("Скрыть легенду");
            getChartContainer().getChart().setLegendVisible(true);
        }
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
