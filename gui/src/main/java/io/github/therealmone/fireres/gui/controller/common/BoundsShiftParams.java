package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import lombok.val;

public class BoundsShiftParams extends AbstractComponent<TitledPane> {

    @FXML
    private VBox boundsShiftVbox;

    @Inject
    private FxmlLoadService fxmlLoadService;

    @Override
    protected void initialize() {
        val boundShift = fxmlLoadService.loadComponent(BoundShift.class, this);
        boundsShiftVbox.getChildren().add(boundShift.getComponent());
    }
}
