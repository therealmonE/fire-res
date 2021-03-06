package io.github.therealmone.fireres.gui.controller.unheated.surface;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.gui.configurer.report.UnheatedSurfaceParametersConfigurer;
import io.github.therealmone.fireres.gui.controller.AbstractComponent;
import io.github.therealmone.fireres.gui.controller.ChartContainer;
import io.github.therealmone.fireres.gui.controller.PresetChanger;
import io.github.therealmone.fireres.gui.controller.PresetContainer;
import io.github.therealmone.fireres.gui.controller.ReportCreator;
import io.github.therealmone.fireres.gui.controller.ReportInclusionChanger;
import io.github.therealmone.fireres.gui.controller.common.SampleTab;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first.FirstGroup;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.second.SecondGroup;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third.ThirdGroup;
import io.github.therealmone.fireres.gui.model.ReportTask;
import io.github.therealmone.fireres.gui.preset.Preset;
import io.github.therealmone.fireres.gui.service.ReportExecutorService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import lombok.Getter;
import lombok.val;

import java.util.List;
import java.util.UUID;

import static io.github.therealmone.fireres.core.config.ReportType.UNHEATED_SURFACE;
import static io.github.therealmone.fireres.gui.util.TabUtils.disableTab;
import static io.github.therealmone.fireres.gui.util.TabUtils.enableTab;

public class UnheatedSurface extends AbstractComponent<ScrollPane>
        implements UnheatedSurfaceReportContainer, ReportInclusionChanger, ReportCreator, PresetChanger {

    @Getter
    private UnheatedSurfaceReport report;

    @Inject
    private UnheatedSurfaceService unheatedSurfaceService;

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportExecutorService reportExecutorService;

    @FXML
    private FirstGroup firstGroupController;

    @FXML
    private SecondGroup secondGroupController;

    @FXML
    private ThirdGroup thirdGroupController;

    @Inject
    private UnheatedSurfaceParametersConfigurer unheatedSurfaceParametersConfigurer;

    @Override
    public Sample getSample() {
        return ((SampleTab) getParent()).getSample();
    }

    @Override
    public ChartContainer getChartContainer() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createReport() {
        val reportId = UUID.randomUUID();

        val task = ReportTask.builder()
                .updatingElementId(reportId)
                .chartContainers(List.of(
                        getFirstGroup().getChartContainer(),
                        getSecondGroup().getChartContainer(),
                        getThirdGroup().getChartContainer()))
                .nodesToLock(List.of(
                        getFirstGroup().getParamsVbox(),
                        getSecondGroup().getParamsVbox(),
                        getThirdGroup().getParamsVbox()))
                .action(() -> {
                    this.report = unheatedSurfaceService.createReport(reportId, getSample());

                    if (!generationProperties.getGeneral().getIncludedReports().contains(UNHEATED_SURFACE)) {
                        excludeReport();
                    }
                })
                .build();

        reportExecutorService.runTask(task);
    }

    @Override
    public void postConstruct() {
        unheatedSurfaceParametersConfigurer.config(this,
                ((PresetContainer) getParent()).getPreset());
    }

    @Override
    public void excludeReport() {
        getSample().removeReport(report);
        disableTab(((SampleTab) getParent()).getUnheatedSurfaceTab());
        generationProperties.getGeneral().getIncludedReports().removeIf(UNHEATED_SURFACE::equals);
    }

    @Override
    public void includeReport() {
        val parent = ((SampleTab) getParent());

        getSample().putReport(report);
        enableTab(parent.getUnheatedSurfaceTab(), parent.getReportsTabPane());
        generationProperties.getGeneral().getIncludedReports().add(UNHEATED_SURFACE);
    }

    @Override
    public void changePreset(Preset preset) {
        unheatedSurfaceParametersConfigurer.config(this, preset);

        getFirstGroup().refresh();
        getSecondGroup().refresh();
        getThirdGroup().refresh();
    }

    public FirstGroup getFirstGroup() {
        return firstGroupController;
    }

    public SecondGroup getSecondGroup() {
        return secondGroupController;
    }

    public ThirdGroup getThirdGroup() {
        return thirdGroupController;
    }
}
