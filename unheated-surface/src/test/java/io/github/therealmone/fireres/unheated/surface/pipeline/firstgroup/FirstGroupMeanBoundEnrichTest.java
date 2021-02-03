package io.github.therealmone.fireres.unheated.surface.pipeline.firstgroup;

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

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.FIRST_GROUP_MEAN_BOUND;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class FirstGroupMeanBoundEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private UnheatedSurfaceService unheatedSurfaceService;

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Test
    public void enrichFirstGroupMeanBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val oldFirstGroupMeanBound = report.getFirstGroup().getMeanBound();
        val oldFirstGroupMeanTemperature = report.getFirstGroup().getMeanTemperature();
        val oldFirstGroupThermocoupleTemperatures = report.getFirstGroup().getThermocoupleTemperatures();

        generationProperties.getGeneral().setEnvironmentTemperature(24);
        reportEnrichPipeline.accept(report, FIRST_GROUP_MEAN_BOUND);

        val newFirstGroupMeanBound = report.getFirstGroup().getMeanBound();
        val newFirstGroupMeanTemperature = report.getFirstGroup().getMeanTemperature();
        val newFirstGroupThermocoupleTemperatures = report.getFirstGroup().getThermocoupleTemperatures();

        assertNotEquals(oldFirstGroupMeanBound, newFirstGroupMeanBound);
        assertNotEquals(oldFirstGroupMeanTemperature, newFirstGroupMeanTemperature);
        assertNotEquals(oldFirstGroupThermocoupleTemperatures, newFirstGroupThermocoupleTemperatures);
    }

}
