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

import static io.github.therealmone.fireres.unheated.surface.pipeline.sample.UnheatedSurfaceSampleEnrichType.SAMPLE_FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class FirstGroupTemperaturesEnrichTest {

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Inject
    private SampleEnrichPipeline<UnheatedSurfaceReport, UnheatedSurfaceSample> sampleEnrichPipeline;

    @Test
    public void enrichFirstGroupThermocoupleBound() {
        val report = new UnheatedSurfaceReport();

        reportEnrichPipeline.accept(report);
        val sample = report.getSamples().get(0);
        val oldFirstGroupMeanTemperature = sample.getFirstGroup().getMeanTemperature();
        val oldFirstGroupThermocoupleTemperatures = sample.getFirstGroup().getThermocoupleTemperatures();

        sampleEnrichPipeline.accept(report, sample, SAMPLE_FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        val newFirstGroupMeanTemperature = sample.getFirstGroup().getMeanTemperature();
        val newFirstGroupThermocoupleTemperatures = sample.getFirstGroup().getThermocoupleTemperatures();

        assertNotEquals(oldFirstGroupMeanTemperature, newFirstGroupMeanTemperature);
        assertNotEquals(oldFirstGroupThermocoupleTemperatures, newFirstGroupThermocoupleTemperatures);
    }

}
