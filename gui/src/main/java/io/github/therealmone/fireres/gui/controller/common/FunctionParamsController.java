package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.Interpolation;
import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.service.InterpolationService;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
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

import java.util.function.Function;

import static java.util.Collections.singletonList;

@SuppressWarnings({"unchecked", "rawtypes"})
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class FunctionParamsController extends AbstractController implements SampleContainer, ReportContainer {

    @ParentController
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
    private TableView<InterpolationPoint> interpolationPointsTableView;

    @FXML
    private TableColumn<InterpolationPoint, Integer> timeColumn;

    @FXML
    private TableColumn<InterpolationPoint, Integer> valueColumn;

    private InterpolationService interpolationService;

    private Function<SampleProperties, Interpolation> propertiesMapper;

    private Runnable postReportUpdateAction;

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
        interpolationService.removeInterpolationPoints(getReport(), singletonList(affectedRow.getItem()));
        interpolationPointsTableView.getItems().remove(affectedRow.getItem());
        postReportUpdateAction.run();
    }

    private void handleRowAddedEvent(Event event) {
        fxmlLoadService.loadInterpolationPointModalWindow(this).show();
    }

    private void handleLinearityCoefficientFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, linearityCoefficientSpinner, () -> {
            interpolationService.updateLinearityCoefficient(getReport(), linearityCoefficientSpinner.getValue());
            postReportUpdateAction.run();
        });
    }

    private void handleDispersionCoefficientFocusChanged(Boolean focusValue) {
        handleSpinnerLostFocus(focusValue, dispersionCoefficientSpinner, () -> {
            interpolationService.updateDispersionCoefficient(getReport(), dispersionCoefficientSpinner.getValue());
            postReportUpdateAction.run();
        });
    }

    @Override
    public Report getReport() {
        return parentController.getReport();
    }
}
