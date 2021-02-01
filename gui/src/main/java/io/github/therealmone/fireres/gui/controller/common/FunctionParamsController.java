package io.github.therealmone.fireres.gui.controller.common;

import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.IntStream;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class FunctionParamsController extends AbstractController implements SampleContainer {

    @ParentController
    private SampleContainer parentController;

    @FXML
    private TableView<InterpolationPoint> interpolationPointsTableView;

    @FXML
    private TableColumn<InterpolationPoint, Integer> timeColumn;

    @FXML
    private TableColumn<InterpolationPoint, Integer> valueColumn;

    @Override
    public SampleProperties getSampleProperties() {
        return parentController.getSampleProperties();
    }

    @Override
    public void postConstruct() {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        initializeTableContextMenu();
        initializeRowContextMenu();

        IntStream.range(0, 4).forEach(i ->
                interpolationPointsTableView.getItems().add(new InterpolationPoint(i, i * 1000)));
    }

    private void initializeTableContextMenu() {
        val contextMenu = createTableContextMenu();

        interpolationPointsTableView.setContextMenu(contextMenu);
    }

    private ContextMenu createTableContextMenu() {
        val contextMenu = new ContextMenu();
        val addPointMenuItem = new MenuItem("Добавить");

        addPointMenuItem.setOnAction(this::handleRowAddedEvent);
        contextMenu.getItems().add(addPointMenuItem);

        return contextMenu;
    }

    private void initializeRowContextMenu() {
        interpolationPointsTableView.setRowFactory(
                tableView -> {
                    val row = new TableRow<InterpolationPoint>();
                    val contextMenu = createRowContextMenu(row);

                    row.contextMenuProperty().bind(
                            Bindings.when(row.emptyProperty().not())
                                    .then(contextMenu)
                                    .otherwise((ContextMenu) null));

                    return row;
                });
    }

    private ContextMenu createRowContextMenu(TableRow<InterpolationPoint> row) {
        val rowMenu = new ContextMenu();
        val addPointMenuItem = new MenuItem("Добавить");
        val removePointMenuItem = new MenuItem("Удалить");

        addPointMenuItem.setOnAction(this::handleRowAddedEvent);
        removePointMenuItem.setOnAction(event -> handleRowDeletedEvent(row));
        rowMenu.getItems().addAll(addPointMenuItem, removePointMenuItem);

        return rowMenu;
    }

    private void handleRowDeletedEvent(TableRow<InterpolationPoint> affectedRow) {
        interpolationPointsTableView.getItems().remove(affectedRow.getItem());
    }

    private void handleRowAddedEvent(Event event) {
        log.info("Interpolation point added");
    }
}
