package io.github.therealmone.fireres.unheated.surface.pipeline.sample.firstgroup;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.GuiceRunner;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceSample;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.unheated.surface.pipeline.sample.UnheatedSurfaceSampleEnrichType.SAMPLE_FIRST_GROUP_MEAN_BOUND;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class FirstGroupMeanBoundEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Inject
    private SampleEnrichPipeline<UnheatedSurfaceReport, UnheatedSurfaceSample> sampleEnrichPipeline;

    @Test
    public void enrichFirstGroupMeanBound() {
        val report = new UnheatedSurfaceReport();

        reportEnrichPipeline.accept(report);
        val sample = report.getSamples().get(0);
        val oldFirstGroupMeanBound = sample.getFirstGroup().getMeanBound();
        val oldFirstGroupMeanTemperature = sample.getFirstGroup().getMeanTemperature();
        val oldFirstGroupThermocoupleTemperatures = sample.getFirstGroup().getThermocoupleTemperatures();

        generationProperties.getGeneral().setEnvironmentTemperature(24);

        sampleEnrichPipeline.accept(report, sample, SAMPLE_FIRST_GROUP_MEAN_BOUND);
        val newFirstGroupMeanBound = sample.getFirstGroup().getMeanBound();
        val newFirstGroupMeanTemperature = sample.getFirstGroup().getMeanTemperature();
        val newFirstGroupThermocoupleTemperatures = sample.getFirstGroup().getThermocoupleTemperatures();

        assertNotEquals(oldFirstGroupMeanBound, newFirstGroupMeanBound);
        assertNotEquals(oldFirstGroupMeanTemperature, newFirstGroupMeanTemperature);
        assertNotEquals(oldFirstGroupThermocoupleTemperatures, newFirstGroupThermocoupleTemperatures);
    }

}
