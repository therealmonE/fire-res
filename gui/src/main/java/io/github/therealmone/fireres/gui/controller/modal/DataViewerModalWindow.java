package io.github.therealmone.fireres.gui.controller.modal;

import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.annotation.ModalWindow;
import io.github.therealmone.fireres.gui.component.DataViewer;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

@LoadableComponent("/component/modal/dataViewerModalWindow.fxml")
@ModalWindow(
        title = "Данные отчета",
        resizable = true,
        modality = Modality.NONE)
public class DataViewerModalWindow extends AbstractComponent<AnchorPane> {

    @ModalWindow.Window
    @Getter
    private Stage window;

    public void setDataViewer(DataViewer dataViewer) {
        this.getComponent().getChildren().add(dataViewer);

        AnchorPane.setBottomAnchor(dataViewer, 0d);
        AnchorPane.setTopAnchor(dataViewer, 0d);
        AnchorPane.setLeftAnchor(dataViewer, 0d);
        AnchorPane.setRightAnchor(dataViewer, 0d);

        this.getComponent().autosize();
    }

}
