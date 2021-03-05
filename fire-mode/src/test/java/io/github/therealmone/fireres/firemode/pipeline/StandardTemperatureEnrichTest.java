package io.github.therealmone.fireres.firemode.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.firemode.FireModeGuiceRunner;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;

@RunWith(FireModeGuiceRunner.class)
public class StandardTemperatureEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FireModeService fireModeService;

    @Inject
    private ReportEnrichPipeline<FireModeReport> reportEnrichPipeline;

    @Test
    public void enrichStandardTemperature() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = fireModeService.createReport(sample);

        report.getProperties().getFunctionForm().getInterpolationPoints().clear();
        reportEnrichPipeline.accept(report);

        val oldMaxAllowedTemperature = report.getMaxAllowedTemperature();
        val oldMinAllowedTemperature = report.getMinAllowedTemperature();
        val oldFurnaceTemperature = report.getFurnaceTemperature();
        val oldStandardTemperature = report.getStandardTemperature();
        val oldMeanTemperature = report.getThermocoupleMeanTemperature();
        val oldThermocoupleTemperatures = report.getThermocoupleTemperatures();

        generationProperties.getGeneral().setEnvironmentTemperature(24);
        reportEnrichPipeline.accept(report, FireModeReportEnrichType.STANDARD_TEMPERATURE);

        val newMaxAllowedTemperature = report.getMaxAllowedTemperature();
        val newMinAllowedTemperature = report.getMinAllowedTemperature();
        val newFurnaceTemperature = report.getFurnaceTemperature();
        val newStandardTemperature = report.getStandardTemperature();
        val newMeanTemperature = report.getThermocoupleMeanTemperature();
        val newThermocoupleTemperatures = report.getThermocoupleTemperatures();

        assertNotEquals(oldMaxAllowedTemperature, newMaxAllowedTemperature);
        assertNotEquals(oldMinAllowedTemperature, newMinAllowedTemperature);
        assertNotEquals(oldFurnaceTemperature, newFurnaceTemperature);
        assertNotEquals(oldStandardTemperature, newStandardTemperature);
        assertNotEquals(oldMeanTemperature, newMeanTemperature);
        assertNotEquals(oldThermocoupleTemperatures, newThermocoupleTemperatures);
    }

}
