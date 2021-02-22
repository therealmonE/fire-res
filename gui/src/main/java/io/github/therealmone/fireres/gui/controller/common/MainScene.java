package io.github.therealmone.fireres.gui.controller.common;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.ReportType;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.top.menu.TopMenuBar;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@LoadableComponent("/scene/mainScene.fxml")
public class MainScene extends AbstractComponent<BorderPane> {

    @Getter
    @Setter
    private Stage primaryStage;

    @Inject
    private GenerationProperties generationProperties;

    @FXML
    private TopMenuBar topMenuBarController;

    @FXML
    private GeneralParams generalParamsController;

    @FXML
    private SamplesTabPane samplesTabPaneController;

    @Override
    public void initialize() {
        generationProperties.getGeneral().getIncludedReports().addAll(Arrays.asList(ReportType.values()));
    }

    public GeneralParams getGeneralParams() {
        return generalParamsController;
    }

    public SamplesTabPane getSamplesTabPane() {
        return samplesTabPaneController;
    }
}
