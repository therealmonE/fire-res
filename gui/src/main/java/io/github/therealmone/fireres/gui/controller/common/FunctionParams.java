package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.FunctionForm;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.Point;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.service.InterpolationService;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractReportUpdaterComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.ReportUpdater;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.modal.InterpolationPointsModalWindow;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
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
import java.util.function.Function;

@SuppressWarnings({"unchecked", "rawtypes"})
@LoadableComponent("/component/common/functionParams.fxml")
public class FunctionParams extends AbstractReportUpdaterComponent<TitledPane>
        implements SampleContainer, ReportContainer {

    @FXML
    @Getter
    private Spinner<Double> childFunctionsDeltaCoefficient;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @FXML
    @Getter
    private Spinner<Double> linearityCoefficient;

    @FXML
    @Getter
    private Spinner<Double> dispersionCoefficient;

    @FXML
    @Getter
    private TableView<Point<?>> interpolationPoints;

    @FXML
    @Getter
    private TableColumn<Point<?>, Integer> timeColumn;

    @FXML
    @Getter
    private TableColumn<Point<?>, Integer> valueColumn;

    @Getter
    @Setter
    private InterpolationService interpolationService;

    @Getter
    @Setter
    private Function<SampleProperties, FunctionForm> propertiesMapper;

    @Getter
    @Setter
    private BiFunction<Integer, Number, Point<?>> interpolationPointConstructor;

    @Getter
    @Setter
    private List<Node> nodesToBlockOnUpdate;

    @Override
    protected void initialize() {
        linearityCoefficient.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleLinearityCoefficientFocusChanged(newValue));

        dispersionCoefficient.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleDispersionCoefficientFocusChanged(newValue));

        childFunctionsDeltaCoefficient.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleChildFunctionsDeltaCoefficienFocusChanged(newValue));
    }

    @Override
    public void postConstruct() {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        initializeTableContextMenu();
        initializeRowContextMenu();
    }

    private void initializeTableContextMenu() {
        val contextMenu = createTableContextMenu();

        interpolationPoints.setContextMenu(contextMenu);
    }

    private ContextMenu createTableContextMenu() {
        val contextMenu = new ContextMenu();
        val addPointMenuItem = new MenuItem("Добавить");

        addPointMenuItem.setOnAction(this::handleRowAddedEvent);
        contextMenu.getItems().add(addPointMenuItem);

        return contextMenu;
    }

    private void initializeRowContextMenu() {
        interpolationPoints.setRowFactory(
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
        Runnable action = () -> {
            interpolationService.removeInterpolationPoint(getReport(), affectedRow.getItem());
            Platform.runLater(() -> interpolationPoints.getItems().remove(affectedRow.getItem()));
        };

        updateReport(action, nodesToBlockOnUpdate);
    }

    private void handleRowAddedEvent(Event event) {
        fxmlLoadService.loadComponent(InterpolationPointsModalWindow.class, this).getWindow().show();
    }

    private void handleLinearityCoefficientFocusChanged(Boolean focusValue) {
        Runnable action = () ->
                interpolationService.updateLinearityCoefficient(getReport(), linearityCoefficient.getValue());

        handleSpinnerLostFocus(focusValue, linearityCoefficient, () ->
                updateReport(action, nodesToBlockOnUpdate));
    }

    private void handleDispersionCoefficientFocusChanged(Boolean focusValue) {
        Runnable action = () ->
                interpolationService.updateDispersionCoefficient(getReport(), dispersionCoefficient.getValue());

        handleSpinnerLostFocus(focusValue, dispersionCoefficient, () ->
                updateReport(action, nodesToBlockOnUpdate));
    }

    private void handleChildFunctionsDeltaCoefficienFocusChanged(Boolean focusValue) {
        Runnable action = () ->
                interpolationService.updateChildFunctionsDeltaCoefficient(getReport(), childFunctionsDeltaCoefficient.getValue());

        handleSpinnerLostFocus(focusValue, childFunctionsDeltaCoefficient, () ->
                updateReport(action, nodesToBlockOnUpdate));
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
