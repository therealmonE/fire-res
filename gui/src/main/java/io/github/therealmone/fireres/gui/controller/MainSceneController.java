package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.ReportType;
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
    private TopMenuBarController topMenuBarController;

    @FXML
    private GeneralParamsController generalParamsController;

    @FXML
    private SamplesTabPaneController samplesTabPaneController;


    @Override
    protected void initialize() {
        topMenuBarController.setMainSceneController(this);
        generalParamsController.setMainSceneController(this);
        samplesTabPaneController.setMainSceneController(this);

        generationProperties.getGeneral().getIncludedReports().addAll(Arrays.asList(ReportType.values()));

        topMenuBarController.postConstruct();
        generalParamsController.postConstruct();
        samplesTabPaneController.postConstruct();
    }
}
