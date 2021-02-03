package io.github.therealmone.fireres.unheated.surface.pipeline.thirdgroup;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.GuiceRunner;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class ThirdGroupTemperaturesEnrichTest {

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Inject
    private UnheatedSurfaceService unheatedSurfaceService;

    @Inject
    private GenerationProperties generationProperties;

    @Test
    public void enrichFirstGroupThermocoupleBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val oldThirdGroupMeanTemperature = report.getThirdGroup().getMeanTemperature();
        val oldThirdGroupThermocoupleTemperatures = report.getThirdGroup().getThermocoupleTemperatures();

        reportEnrichPipeline.accept(report, THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);

        val newThirdGroupMeanTemperature = report.getThirdGroup().getMeanTemperature();
        val newThirdGroupThermocoupleTemperatures = report.getThirdGroup().getThermocoupleTemperatures();

        assertNotEquals(oldThirdGroupMeanTemperature, newThirdGroupMeanTemperature);
        assertNotEquals(oldThirdGroupThermocoupleTemperatures, newThirdGroupThermocoupleTemperatures);
    }

}
