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
import io.github.therealmone.fireres.gui.controller.ReportUpdater;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import lombok.val;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
@LoadableComponent("/component/common/boundsShiftParams.fxml")
public class BoundsShiftParams extends AbstractReportUpdaterComponent<TitledPane>
        implements SampleContainer, ReportContainer {

    @FXML
    private VBox boundsShiftVbox;

    @Inject
    private FxmlLoadService fxmlLoadService;

    public void addBoundShift(
            String label,
            List<Node> nodesToBlockOnUpdate,
            Function<ReportProperties, io.github.therealmone.fireres.core.model.BoundShift<?>> propertyMapper,
            Consumer<Point<?>> shiftAddedConsumer,
            Consumer<Point<?>> shiftRemovedConsumer,
            BiFunction<Integer, Number, Point<?>> shiftPointConstructor
    ) {
        val boundShift = fxmlLoadService.loadComponent(BoundShift.class, this);

        boundShift.setLabel(label);
        boundShift.setNodesToBlockOnUpdate(nodesToBlockOnUpdate);
        boundShift.setShiftAddedConsumer(shiftAddedConsumer);
        boundShift.setShiftRemovedConsumer(shiftRemovedConsumer);
        boundShift.setShiftPointConstructor(shiftPointConstructor);
        boundShift.setPropertyMapper(propertyMapper);

        boundsShiftVbox.getChildren().add(boundShift.getComponent());
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
