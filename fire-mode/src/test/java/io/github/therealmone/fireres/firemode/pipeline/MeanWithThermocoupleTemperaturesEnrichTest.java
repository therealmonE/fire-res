package io.github.therealmone.fireres.firemode.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.firemode.GuiceRunner;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.firemode.pipeline.FireModeReportEnrichType.MEAN_WITH_THERMOCOUPLE_TEMPERATURES;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class MeanWithThermocoupleTemperaturesEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FireModeService fireModeService;

    @Inject
    private ReportEnrichPipeline<FireModeReport> reportEnrichPipeline;

    @Test
    public void enrichSamplePressure() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = fireModeService.createReport(sample);

        val oldSampleMeanTemperature = report.getThermocoupleMeanTemperature();
        val oldSampleThermocoupleTemperatures = report.getThermocoupleTemperatures();

        reportEnrichPipeline.accept(report, MEAN_WITH_THERMOCOUPLE_TEMPERATURES);
        val newSampleMeanTemperature = report.getThermocoupleMeanTemperature();
        val newSampleThermocoupleTemperatures = report.getThermocoupleTemperatures();

        assertNotEquals(oldSampleMeanTemperature, newSampleMeanTemperature);
        assertNotEquals(oldSampleThermocoupleTemperatures, newSampleThermocoupleTemperatures);
    }

}
