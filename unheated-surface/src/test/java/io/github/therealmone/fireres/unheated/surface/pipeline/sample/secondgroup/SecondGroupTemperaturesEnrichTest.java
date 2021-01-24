package io.github.therealmone.fireres.unheated.surface.pipeline.sample.secondgroup;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.GuiceRunner;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceSample;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.unheated.surface.pipeline.sample.UnheatedSurfaceSampleEnrichType.SAMPLE_SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SecondGroupTemperaturesEnrichTest {

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Inject
    private SampleEnrichPipeline<UnheatedSurfaceReport, UnheatedSurfaceSample> sampleEnrichPipeline;

    @Test
    public void enrichFirstGroupThermocoupleBound() {
        val report = new UnheatedSurfaceReport();

        reportEnrichPipeline.accept(report);
        val sample = report.getSamples().get(0);
        val oldSecondGroupMeanTemperature = sample.getSecondGroup().getMeanTemperature();
        val oldSecondGroupThermocoupleTemperatures = sample.getSecondGroup().getThermocoupleTemperatures();

        sampleEnrichPipeline.accept(report, sample, SAMPLE_SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        val newSecondGroupMeanTemperature = sample.getSecondGroup().getMeanTemperature();
        val newSecondGroupThermocoupleTemperatures = sample.getSecondGroup().getThermocoupleTemperatures();

        assertNotEquals(oldSecondGroupMeanTemperature, newSecondGroupMeanTemperature);
        assertNotEquals(oldSecondGroupThermocoupleTemperatures, newSecondGroupThermocoupleTemperatures);
    }

}
