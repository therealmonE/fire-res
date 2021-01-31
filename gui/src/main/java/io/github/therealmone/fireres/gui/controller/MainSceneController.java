package io.github.therealmone.fireres.gui.controller;

import javafx.fxml.FXML;

public class MainSceneController extends AbstractController {

    @FXML
    private TopMenuBarController topMenuBarController;

    @FXML
    private GeneralParamsController generalParamsController;

    @FXML
    private SamplesTabPaneController samplesTabPaneController;

    @FXML
    private BottomButtonBarController bottomButtonBarController;

    @Override
    protected void initialize() {
        topMenuBarController.setMainSceneController(this);
        generalParamsController.setMainSceneController(this);
        samplesTabPaneController.setMainSceneController(this);

        topMenuBarController.postConstruct();
        generalParamsController.postConstruct();
        samplesTabPaneController.postConstruct();
        bottomButtonBarController.postConstruct();
    }
}
