package io.github.therealmone.fireres.unheated.surface.pipeline.sample.secondgroup;

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

import static io.github.therealmone.fireres.unheated.surface.pipeline.sample.UnheatedSurfaceSampleEnrichType.SAMPLE_SECOND_GROUP_THERMOCOUPLE_BOUND;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SecondGroupThermocoupleBoundEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Inject
    private SampleEnrichPipeline<UnheatedSurfaceReport, UnheatedSurfaceSample> sampleEnrichPipeline;

    @Test
    public void enrichFirstGroupThermocoupleBound() {
        val report = new UnheatedSurfaceReport();

        reportEnrichPipeline.accept(report);
        val sample = report.getSamples().get(0);
        val oldSecondGroupThermocoupleBound = sample.getSecondGroup().getThermocoupleBound();
        val oldSecondGroupMeanTemperature = sample.getSecondGroup().getMeanTemperature();
        val oldSecondGroupThermocoupleTemperatures = sample.getSecondGroup().getThermocoupleTemperatures();

        generationProperties.getSampleById(sample.getId()).getUnheatedSurface().getSecondGroup().setBound(400);

        sampleEnrichPipeline.accept(report, sample, SAMPLE_SECOND_GROUP_THERMOCOUPLE_BOUND);
        val newSecondGroupThermocoupleBound = sample.getSecondGroup().getThermocoupleBound();
        val newSecondGroupMeanTemperature = sample.getSecondGroup().getMeanTemperature();
        val newSecondGroupThermocoupleTemperatures = sample.getSecondGroup().getThermocoupleTemperatures();

        assertNotEquals(oldSecondGroupThermocoupleBound, newSecondGroupThermocoupleBound);
        assertNotEquals(oldSecondGroupMeanTemperature, newSecondGroupMeanTemperature);
        assertNotEquals(oldSecondGroupThermocoupleTemperatures, newSecondGroupThermocoupleTemperatures);
    }

}
