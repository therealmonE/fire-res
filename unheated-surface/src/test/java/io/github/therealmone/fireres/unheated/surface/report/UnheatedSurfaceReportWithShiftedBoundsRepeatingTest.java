package io.github.therealmone.fireres.unheated.surface.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.unheated.surface.UnheatedSurfaceGuiceRunner;
import io.github.therealmone.fireres.unheated.surface.config.PrimaryGroupBoundsShift;
import io.github.therealmone.fireres.unheated.surface.config.SecondaryGroupBoundsShift;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.unheated.surface.UnheatedSurfaceTestUtils.assertUnheatedSurfaceReportIsValid;

@RunWith(UnheatedSurfaceGuiceRunner.class)
public class UnheatedSurfaceReportWithShiftedBoundsRepeatingTest {

    private static final Integer ATTEMPTS = 100;

    @Inject
    private UnheatedSurfaceService unheatedSurfaceService;

    @Inject
    private GenerationProperties generationProperties;

    @Before
    public void setUpBoundsShift() {
        val sampleProperties = generationProperties.getSamples().get(0);
        val reportProperties = sampleProperties
                .getReportPropertiesByClass(UnheatedSurfaceProperties.class)
                .orElseThrow();

        reportProperties.getFirstGroup().getFunctionForm().getInterpolationPoints().clear();
        reportProperties.getSecondGroup().getFunctionForm().getInterpolationPoints().clear();
        reportProperties.getThirdGroup().getFunctionForm().getInterpolationPoints().clear();

        setUpPrimaryBoundsShift(reportProperties.getFirstGroup().getBoundsShift());
        setUpSecondaryBoundsShift(reportProperties.getSecondGroup().getBoundsShift());
        setUpSecondaryBoundsShift(reportProperties.getThirdGroup().getBoundsShift());
    }

    private void setUpPrimaryBoundsShift(PrimaryGroupBoundsShift primaryBoundsShift) {
        primaryBoundsShift.getMaxAllowedMeanTemperatureShift().add(new IntegerPoint(10, -5));
        primaryBoundsShift.getMaxAllowedThermocoupleTemperatureShift().add(new IntegerPoint(10, -5));

        primaryBoundsShift.getMaxAllowedMeanTemperatureShift().add(new IntegerPoint(50, 100));
        primaryBoundsShift.getMaxAllowedThermocoupleTemperatureShift().add(new IntegerPoint(50, 100));
    }

    private void setUpSecondaryBoundsShift(SecondaryGroupBoundsShift secondaryBoundsShift) {
        secondaryBoundsShift.getMaxAllowedTemperatureShift().add(new IntegerPoint(10, -5));
        secondaryBoundsShift.getMaxAllowedTemperatureShift().add(new IntegerPoint(50, 100));
    }

    @Test
    public void provideReportTest() {
        for (int i = 0; i < ATTEMPTS; i++) {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = unheatedSurfaceService.createReport(sample);

            assertUnheatedSurfaceReportIsValid(report);
        }
    }

}
