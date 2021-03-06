package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.modal.BoundsShiftModalWindow;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import lombok.Getter;
import lombok.val;

import java.util.UUID;

@SuppressWarnings({"rawtypes"})
@LoadableComponent("/component/common/boundShift.fxml")
public class BoundShift extends AbstractReportUpdaterComponent<TitledPane>
        implements SampleContainer, ReportContainer {

    @Inject
    private FxmlLoadService fxmlLoadService;

    @FXML
    @Getter
    private TableView<Point<?>> boundShiftTable;

    @Override
    public Report getReport() {
        return ((ReportContainer) getParent()).getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return ((ReportContainer) getParent()).getChartContainer();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getId();
    }

    @Override
    public Sample getSample() {
        return ((ReportContainer) getParent()).getSample();
    }

    @Override
    public void postConstruct() {
        initializeTableContextMenu();
        initializeRowContextMenu();
    }

    private void initializeTableContextMenu() {
        val contextMenu = createTableContextMenu();

        boundShiftTable.setContextMenu(contextMenu);
    }

    private ContextMenu createTableContextMenu() {
        val contextMenu = new ContextMenu();
        val addPointMenuItem = new MenuItem("Добавить");

        addPointMenuItem.setOnAction(this::handleRowAddedEvent);
        contextMenu.getItems().add(addPointMenuItem);

        return contextMenu;
    }

    private void initializeRowContextMenu() {
        boundShiftTable.setRowFactory(
                tableView -> {
                    val row = new TableRow<Point<?>>();
                    val contextMenu = createRowContextMenu(row);

                    row.contextMenuProperty().bind(
                            Bindings.when(row.emptyProperty().not())
                                    .then(contextMenu)
                                    .otherwise((ContextMenu) null));

                    return row;
                });
    }

    private ContextMenu createRowContextMenu(TableRow<Point<?>> row) {
        val rowMenu = new ContextMenu();
        val addPointMenuItem = new MenuItem("Добавить");
        val removePointMenuItem = new MenuItem("Удалить");

        addPointMenuItem.setOnAction(this::handleRowAddedEvent);
        rowMenu.getItems().addAll(addPointMenuItem, removePointMenuItem);

        return rowMenu;
    }


    private void handleRowAddedEvent(Event event) {
        fxmlLoadService.loadComponent(BoundsShiftModalWindow.class, this).getWindow().show();
    }
}
