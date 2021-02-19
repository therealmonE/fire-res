package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.Interpolation;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.service.InterpolationService;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterController;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"unchecked", "rawtypes"})
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class FunctionParamsController extends AbstractReportUpdaterController implements SampleContainer, ReportContainer {

    private ReportContainer parentController;

    @Inject
    private ResetSettingsService resetSettingsService;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @FXML
    private Spinner<Double> linearityCoefficientSpinner;

    @FXML
    private Spinner<Double> dispersionCoefficientSpinner;

    @FXML
    private TableView<Point<?>> interpolationPointsTableView;

    @FXML
    private TableColumn<Point<?>, Integer> timeColumn;

    @FXML
    private TableColumn<Point<?>, Integer> valueColumn;

    private InterpolationService interpolationService;

    private Function<SampleProperties, Interpolation> propertiesMapper;

    private BiFunction<Integer, Number, Point<?>> interpolationPointConstructor;

    @Override
    public Sample getSample() {
        return parentController.getSample();
    }

    @Override
    protected void initialize() {
        linearityCoefficientSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleLinearityCoefficientFocusChanged(newValue));

        dispersionCoefficientSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleDispersionCoefficientFocusChanged(newValue));
    }

    @Override
    public void postConstruct() {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        initializeTableContextMenu();
        initializeRowContextMenu();

        resetSettingsService.resetFunctionParameters(this);
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

    private void handleRowDeletedEvent(TableRow<Point<?>> affectedRow) {
        updateReport(() -> {
            interpolationService.removeInterpolationPoint(getReport(), affectedRow.getItem());
            Platform.runLater(() -> interpolationPointsTableView.getItems().remove(affectedRow.getItem()));
        });
    }

    private void handleRowAddedEvent(Event event) {
        fxmlLoadService.loadInterpolationPointModalWindow(this).show();
    }

    private void handleLinearityCoefficientFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, linearityCoefficientSpinner, () ->
                updateReport(() -> interpolationService.updateLinearityCoefficient(getReport(), linearityCoefficientSpinner.getValue())));
    }

    private void handleDispersionCoefficientFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, dispersionCoefficientSpinner, () ->
                updateReport(() -> interpolationService.updateDispersionCoefficient(getReport(), dispersionCoefficientSpinner.getValue())));
    }

    @Override
    public Report getReport() {
        return parentController.getReport();
    }

    @Override
    public ChartContainer getChartContainer() {
        return parentController.getChartContainer();
    }

    @Override
    protected UUID getReportId() {
        return getReport().getId();
    }
}
