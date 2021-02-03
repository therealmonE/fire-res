package io.github.therealmone.fireres.cli;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excel.ReportConstructor;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.nio.file.Path;

import static io.github.therealmone.fireres.core.config.ReportType.EXCESS_PRESSURE;
import static io.github.therealmone.fireres.core.config.ReportType.FIRE_MODE;
import static io.github.therealmone.fireres.core.config.ReportType.HEAT_FLOW;
import static io.github.therealmone.fireres.core.config.ReportType.UNHEATED_SURFACE;

@Slf4j
public class Application {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportConstructor reportConstructor;

    @Inject
    private FireModeService fireModeService;

    @Inject
    private ExcessPressureService excessPressureService;

    @Inject
    private UnheatedSurfaceService unheatedSurfaceService;

    @Inject
    private HeatFlowService heatFlowService;

    public void run() {
        generationProperties.getSamples().forEach(sampleProperties -> {
            val outputFile = Path.of(
                    generationProperties.getGeneral().getOutputPath(),
                    sampleProperties.getName() + ".xlsx"
            ).toFile();

            val sample = new Sample(sampleProperties);

            createReports(sample);

            reportConstructor.construct(generationProperties.getGeneral(), sample, outputFile);
        });
    }

    private void createReports(Sample sample) {
        val includedReports = generationProperties.getGeneral().getIncludedReports();

        if (includedReports.contains(FIRE_MODE)) {
            fireModeService.createReport(sample);
        }

        if (includedReports.contains(EXCESS_PRESSURE)) {
            excessPressureService.createReport(sample);
        }

        if (includedReports.contains(UNHEATED_SURFACE)) {
            unheatedSurfaceService.createReport(sample);
        }

        if (includedReports.contains(HEAT_FLOW)) {
            heatFlowService.createReport(sample);
        }
    }
}
