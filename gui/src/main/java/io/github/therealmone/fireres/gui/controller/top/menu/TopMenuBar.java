package io.github.therealmone.fireres.gui.controller.top.menu;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.modal.AboutProgramModalWindow;
import io.github.therealmone.fireres.gui.controller.modal.export.ExportModalWindow;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

@LoadableComponent("/component/topMenuBar.fxml")
public class TopMenuBar extends AbstractComponent<MenuBar> {

    public static final String USER_GUIDE_LINK = "https://github.com/therealmonE/fire-res/wiki";

    @Inject
    private FxmlLoadService fxmlLoadService;

    @Inject
    private HostServices hostServices;

    @FXML
    public void openExportModalWindow() {
        fxmlLoadService.loadComponent(ExportModalWindow.class, getParent()).getWindow().show();
    }

    @FXML
    public void openAboutProgramModalWindow() {
        fxmlLoadService.loadComponent(AboutProgramModalWindow.class, getParent()).getWindow().show();
    }

    @FXML
    public void openUserGuideLink() {
        hostServices.showDocument(USER_GUIDE_LINK);
    }

}
