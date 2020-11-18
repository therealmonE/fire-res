package io.github.therealmone.fireres.unheated.surface.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.unheated.surface.GuiceRunner;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.unheated.surface.TestUtils.assertUnheatedSurfaceReportIsValid;

@RunWith(GuiceRunner.class)
public class UnheatedSurfaceReportRepeatingTest {

    private static final Integer ATTEMPTS = 100;

    @Inject
    private UnheatedSurfaceReportProvider reportProvider;

    @Test
    public void provideReportTest() {
        for (int i = 0; i < ATTEMPTS; i++) {
            val report = reportProvider.get();

            assertUnheatedSurfaceReportIsValid(report);
        }
    }

}
