package io.github.therealmone.fireres.unheated.surface.pipeline.secondgroup;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.UnheatedSurfaceGuiceRunner;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static org.junit.Assert.assertNotEquals;

@RunWith(UnheatedSurfaceGuiceRunner.class)
public class SecondGroupTemperaturesEnrichTest {

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

        val oldSecondGroupMeanTemperature = report.getSecondGroup().getMeanTemperature();
        val oldSecondGroupThermocoupleTemperatures = report.getSecondGroup().getThermocoupleTemperatures();

        reportEnrichPipeline.accept(report, SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);

        val newSecondGroupMeanTemperature = report.getSecondGroup().getMeanTemperature();
        val newSecondGroupThermocoupleTemperatures = report.getSecondGroup().getThermocoupleTemperatures();

        assertNotEquals(oldSecondGroupMeanTemperature, newSecondGroupMeanTemperature);
        assertNotEquals(oldSecondGroupThermocoupleTemperatures, newSecondGroupThermocoupleTemperatures);
    }

}
