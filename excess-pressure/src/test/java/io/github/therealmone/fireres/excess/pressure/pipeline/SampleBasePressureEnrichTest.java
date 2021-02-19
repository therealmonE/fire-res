package io.github.therealmone.fireres.excess.pressure.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.excess.pressure.ExcessPressureGuiceRunner;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.excess.pressure.pipeline.ExcessPressureReportEnrichType.BASE_PRESSURE;
import static org.junit.Assert.assertNotEquals;

@RunWith(ExcessPressureGuiceRunner.class)
public class SampleBasePressureEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ExcessPressureService excessPressureService;

    @Inject
    private ReportEnrichPipeline<ExcessPressureReport> reportEnrichPipeline;

    @Test
    public void enrichBasePressure() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = excessPressureService.createReport(sample);

        val oldBasePressure = report.getBasePressure();

        report.getProperties().setBasePressure(10000.0);
        reportEnrichPipeline.accept(report, BASE_PRESSURE);

        val newBasePressure = report.getBasePressure();

        assertNotEquals(oldBasePressure, newBasePressure);
    }

}
