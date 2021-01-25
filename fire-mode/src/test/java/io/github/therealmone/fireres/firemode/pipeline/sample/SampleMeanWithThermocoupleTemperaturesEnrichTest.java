package io.github.therealmone.fireres.firemode.pipeline.sample;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.firemode.GuiceRunner;
import io.github.therealmone.fireres.firemode.model.FireModeSample;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.firemode.pipeline.sample.FireModeSampleEnrichType.SAMPLE_MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SampleMeanWithThermocoupleTemperaturesEnrichTest {

    @Inject
    private ReportEnrichPipeline<FireModeReport> reportEnrichPipeline;

    @Inject
    private SampleEnrichPipeline<FireModeReport, FireModeSample> sampleEnrichPipeline;

    @Test
    public void enrichSamplePressure() {
        val report = new FireModeReport();

        reportEnrichPipeline.accept(report);
        val sample = report.getSamples().get(0);
        val oldSampleMeanTemperature = sample.getThermocoupleMeanTemperature();
        val oldSampleThermocoupleTemperatures = sample.getThermocoupleTemperatures();

        sampleEnrichPipeline.accept(report, sample, SAMPLE_MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        val newSampleMeanTemperature = sample.getThermocoupleMeanTemperature();
        val newSampleThermocoupleTemperatures = sample.getThermocoupleTemperatures();

        assertNotEquals(oldSampleMeanTemperature, newSampleMeanTemperature);
        assertNotEquals(oldSampleThermocoupleTemperatures, newSampleThermocoupleTemperatures);
    }

}
