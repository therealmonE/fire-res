package io.github.therealmone.fireres.firemode.pipeline.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.firemode.GuiceRunner;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.firemode.pipeline.report.FireModeReportEnrichType.STANDARD_TEMPERATURE;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class StandardTemperatureEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportEnrichPipeline<FireModeReport> reportEnrichPipeline;

    @Test
    public void enrichStandardTemperature() {
        val report = new FireModeReport();

        generationProperties.getSamples().get(0).getFireMode().getInterpolationPoints().clear();

        reportEnrichPipeline.accept(report);
        val oldSamples = report.getSamples();
        val oldMaxAllowedTemperature = report.getMaxAllowedTemperature();
        val oldMinAllowedTemperature = report.getMinAllowedTemperature();
        val oldFurnaceTemperature = report.getFurnaceTemperature();
        val oldStandardTemperature = report.getStandardTemperature();

        generationProperties.getGeneral().setEnvironmentTemperature(24);

        reportEnrichPipeline.accept(report, STANDARD_TEMPERATURE);
        val newSamples = report.getSamples();
        val newMaxAllowedTemperature = report.getMaxAllowedTemperature();
        val newMinAllowedTemperature = report.getMinAllowedTemperature();
        val newFurnaceTemperature = report.getFurnaceTemperature();
        val newStandardTemperature = report.getStandardTemperature();

        assertNotEquals(oldSamples, newSamples);
        assertNotEquals(oldMaxAllowedTemperature, newMaxAllowedTemperature);
        assertNotEquals(oldMinAllowedTemperature, newMinAllowedTemperature);
        assertNotEquals(oldFurnaceTemperature, newFurnaceTemperature);
        assertNotEquals(oldStandardTemperature, newStandardTemperature);
    }

}
