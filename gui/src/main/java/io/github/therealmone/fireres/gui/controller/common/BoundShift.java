package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.ReportProperties;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.Resettable;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.modal.BoundShiftModalWindow;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings({"rawtypes"})
@LoadableComponent("/component/common/boundShift.fxml")
public class BoundShift extends AbstractReportUpdaterComponent<TitledPane>
        implements SampleContainer, ReportContainer, Resettable {

    @FXML
    private TableColumn<Point<?>, Integer> timeColumn;

    @FXML
    private TableColumn<Point<?>, Integer> valueColumn;

    @FXML
    private Label label;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @FXML
    @Getter
    private TableView<Point<?>> boundShiftTable;

    @Setter
    @Getter
    private Consumer<Point<?>> shiftAddedConsumer;

    @Setter
    private Consumer<Point<?>> shiftRemovedConsumer;

    @Getter
    @Setter
    private BiFunction<Integer, Number, Point<?>> shiftPointConstructor;

    @Setter
    @Getter
    private List<Node> nodesToBlockOnUpdate;

    @Setter
    private Function<ReportProperties, io.github.therealmone.fireres.core.model.BoundShift<?>> propertyMapper;

    @Override
    public void postConstruct() {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        initializeTableContextMenu();
        initializeRowContextMenu();
    }

    @Override
    public void reset() {
        boundShiftTable.getItems().clear();
        propertyMapper.apply(getReport().getProperties()).clear();
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
        removePointMenuItem.setOnAction(event -> handleRowDeletedEvent(row));
        rowMenu.getItems().addAll(addPointMenuItem, removePointMenuItem);

        return rowMenu;
    }

    private void handleRowAddedEvent(Event event) {
        fxmlLoadService.loadComponent(BoundShiftModalWindow.class, this).getWindow().show();
    }

    private void handleRowDeletedEvent(TableRow<Point<?>> row) {
        Runnable action = () -> {
            shiftRemovedConsumer.accept(row.getItem());
            Platform.runLater(() -> boundShiftTable.getItems().remove(row.getItem()));
        };

        updateReport(action, nodesToBlockOnUpdate);
    }

    public void setLabel(String label) {
        this.label.setText(label);
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
    protected UUID getReportId() {
        return getReport().getId();
    }

    @Override
    public Sample getSample() {
        return ((ReportContainer) getParent()).getSample();
    }

}
