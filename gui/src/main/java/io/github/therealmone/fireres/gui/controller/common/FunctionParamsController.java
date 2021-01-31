package io.github.therealmone.fireres.gui.controller.common;

import io.github.therealmone.fireres.core.config.InterpolationPoint;
import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.SampleTabController;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressureParamsController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.util.stream.IntStream;

@EqualsAndHashCode(callSuper = true)
@Data

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

    }
}
