package io.github.therealmone.fireres.unheated.surface.pipeline.secondgroup;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.GuiceRunner;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.unheated.surface.pipeline.UnheatedSurfaceReportEnrichType.SECOND_GROUP_THERMOCOUPLE_BOUND;
import static org.junit.Assert.assertNotEquals;

@RunWith(GuiceRunner.class)
public class SecondGroupThermocoupleBoundEnrichTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportEnrichPipeline<UnheatedSurfaceReport> reportEnrichPipeline;

    @Inject
    private UnheatedSurfaceService unheatedSurfaceService;

    @Test
    public void enrichFirstGroupThermocoupleBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val oldSecondGroupThermocoupleBound = report.getSecondGroup().getThermocoupleBound();
        val oldSecondGroupMeanTemperature = report.getSecondGroup().getMeanTemperature();
        val oldSecondGroupThermocoupleTemperatures = report.getSecondGroup().getThermocoupleTemperatures();

        generationProperties.getSampleById(sample.getId()).getUnheatedSurface().getSecondGroup().setBound(400);
        reportEnrichPipeline.accept(report, SECOND_GROUP_THERMOCOUPLE_BOUND);

        val newSecondGroupThermocoupleBound = report.getSecondGroup().getThermocoupleBound();
        val newSecondGroupMeanTemperature = report.getSecondGroup().getMeanTemperature();
        val newSecondGroupThermocoupleTemperatures = report.getSecondGroup().getThermocoupleTemperatures();

        assertNotEquals(oldSecondGroupThermocoupleBound, newSecondGroupThermocoupleBound);
        assertNotEquals(oldSecondGroupMeanTemperature, newSecondGroupMeanTemperature);
        assertNotEquals(oldSecondGroupThermocoupleTemperatures, newSecondGroupThermocoupleTemperatures);
    }

}
