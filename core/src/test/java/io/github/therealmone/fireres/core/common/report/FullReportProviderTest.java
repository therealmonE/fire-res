package io.github.therealmone.fireres.core.common.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.GuiceRunner;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.TestUtils.assertExcessPressure;
import static io.github.therealmone.fireres.core.TestUtils.assertFireMode;
import static io.github.therealmone.fireres.core.TestUtils.assertUnheatedSurface;

@RunWith(GuiceRunner.class)
public class FullReportProviderTest {

    private static final Integer CYCLES = 1000;

    @Inject
    private FullReportProvider fullReportProvider;

    @Test
    public void buildMultipleTimes() {
        for (int i = 0; i < CYCLES; i++) {
            build();
        }
    }

    @Test
    public void build() {
        val report = fullReportProvider.get();

        assertFireMode(report);
        assertExcessPressure(report);
        assertUnheatedSurface(report);
    }
}