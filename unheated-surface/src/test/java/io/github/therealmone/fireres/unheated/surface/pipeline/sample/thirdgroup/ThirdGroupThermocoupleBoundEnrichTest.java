package io.github.therealmone.fireres.unheated.surface.pipeline.sample.thirdgroup;

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

import static io.github.therealmone.fireres.unheated.surface.pipeline.sample.UnheatedSurfaceSampleEnrichType.SAMPLE_THIRD_GROUP_THERMOCOUPLE_BOUND;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class ThirdGroupThermocoupleBoundEnrichTest {

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
        val oldThirdGroupThermocoupleBound = sample.getThirdGroup().getThermocoupleBound();
        val oldThirdGroupMeanTemperature = sample.getThirdGroup().getMeanTemperature();
        val oldThirdGroupThermocoupleTemperatures = sample.getThirdGroup().getThermocoupleTemperatures();

        generationProperties.getSampleById(sample.getId()).getUnheatedSurface().getThirdGroup().setBound(400);

        sampleEnrichPipeline.accept(report, sample, SAMPLE_THIRD_GROUP_THERMOCOUPLE_BOUND);
        val newThirdGroupThermocoupleBound = sample.getThirdGroup().getThermocoupleBound();
        val newThirdGroupMeanTemperature = sample.getThirdGroup().getMeanTemperature();
        val newThirdGroupThermocoupleTemperatures = sample.getThirdGroup().getThermocoupleTemperatures();

        assertNotEquals(oldThirdGroupThermocoupleBound, newThirdGroupThermocoupleBound);
        assertNotEquals(oldThirdGroupMeanTemperature, newThirdGroupMeanTemperature);
        assertNotEquals(oldThirdGroupThermocoupleTemperatures, newThirdGroupThermocoupleTemperatures);
    }

}
