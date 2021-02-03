package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.Interpolation;
import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.Controller;
import io.github.therealmone.fireres.gui.controller.ReportContainer;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.fire.mode.FireModePaneController;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlowPaneController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.first.thermocouple.group.FirstThermocoupleGroupPaneController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.second.thermocouple.group.SecondThermocoupleGroupPaneController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.third.thermocouple.group.ThirdThermocoupleGroupPaneController;
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

import java.util.Map;
import java.util.function.Function;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class FunctionParamsController extends AbstractController implements SampleContainer, ReportContainer {

    private static final Map<Class<? extends Controller>, Function<SampleProperties, Interpolation>> MAPPER_MAP = Map.of(
            FireModePaneController.class, SampleProperties::getFireMode,
            HeatFlowPaneController.class, SampleProperties::getHeatFlow,
            FirstThermocoupleGroupPaneController.class, sample -> sample.getUnheatedSurface().getFirstGroup(),
            SecondThermocoupleGroupPaneController.class, sample -> sample.getUnheatedSurface().getSecondGroup(),
            ThirdThermocoupleGroupPaneController.class, sample -> sample.getUnheatedSurface().getThirdGroup()
    );

    @ParentController
    private ReportContainer parentController;

    @Inject
    private ResetSettingsService resetSettingsService;

    @FXML
    private Spinner<Double> linearSpinner;

    @FXML
    private Spinner<Double> dispersionSpinner;

    @FXML
    private TableView<InterpolationPoint> interpolationPointsTableView;

    @FXML
    private TableColumn<InterpolationPoint, Integer> timeColumn;

    @FXML
    private TableColumn<InterpolationPoint, Integer> valueColumn;

    @Override
    public Sample getSample() {
        return parentController.getSample();
    }

    @Override
    protected void initialize() {
        linearSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSpinnerFocusChanged(newValue, linearSpinner));

        dispersionSpinner.focusedProperty().addListener((observable, oldValue, newValue) ->
                handleSpinnerFocusChanged(newValue, dispersionSpinner));
    }

    private void handleSpinnerFocusChanged (Boolean newValue, Spinner<?> spinner) {
        if (!newValue) {
            log.info("Spinner {} lost focus, sample id: {}", spinner.getId(), getSample().getId());
            commitSpinner(spinner);
        }
    }
    @Override
    public void postConstruct() {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        initializeTableContextMenu();
        initializeRowContextMenu();

        resetSettingsService.resetFunctionParameters(this);
    }

    public Function<SampleProperties, Interpolation> getSamplePropertiesMapper() {
        return MAPPER_MAP.get(parentController.getClass());
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

    @Override
    public Report getReport() {
        return parentController.getReport();
    }
}
