package io.github.therealmone.fireres.unheated.surface.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.unheated.surface.UnheatedSurfaceGuiceRunner;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.test.TestUtils.repeatTest;
import static io.github.therealmone.fireres.unheated.surface.UnheatedSurfaceTestUtils.assertUnheatedSurfaceReportIsValid;

@RunWith(UnheatedSurfaceGuiceRunner.class)
public class UnheatedSurfaceReportRepeatingTest {

    @Inject
    private UnheatedSurfaceService unheatedSurfaceService;

    @Inject
    private GenerationProperties generationProperties;

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = unheatedSurfaceService.createReport(sample);

            assertUnheatedSurfaceReportIsValid(report);
        });
    }

}
