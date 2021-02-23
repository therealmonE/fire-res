package io.github.therealmone.fireres.gui.controller.modal;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.annotation.ModalWindow;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;

@ModalWindow(title = "О программе")
@LoadableComponent("/component/modal/aboutProgramModalWindow.fxml")
public class AboutProgramModalWindow extends AbstractComponent<AnchorPane> {

    @ModalWindow.Window
    @Getter
    private Stage window;

    @FXML
    private Hyperlink repositoryLink;

    @Inject
    private HostServices hostServices;

    public void openGithubLink() {
        hostServices.showDocument(repositoryLink.getText());
    }

}

