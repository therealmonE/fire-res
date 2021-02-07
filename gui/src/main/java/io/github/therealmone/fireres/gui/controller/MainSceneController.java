package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.ReportType;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;

@EqualsAndHashCode(callSuper = true)
@Data
public class MainSceneController extends AbstractController {

    private Stage primaryStage;

    @Inject
    private GenerationProperties generationProperties;

    @FXML
    @ChildController
    private TopMenuBarController topMenuBarController;

    @FXML
    @ChildController
    private GeneralParamsController generalParamsController;

    @FXML
    @ChildController
    private SamplesTabPaneController samplesTabPaneController;

    @FXML
    @ChildController
    private BottomButtonBarController bottomButtonBarController;

    @Override
    protected void initialize() {
        topMenuBarController.setMainSceneController(this);
        generalParamsController.setMainSceneController(this);
        samplesTabPaneController.setMainSceneController(this);
        bottomButtonBarController.setMainSceneController(this);

        generationProperties.getGeneral().getIncludedReports().addAll(Arrays.asList(ReportType.values()));

        topMenuBarController.postConstruct();
        generalParamsController.postConstruct();
        samplesTabPaneController.postConstruct();
        bottomButtonBarController.postConstruct();
    }
}
