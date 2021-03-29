package io.github.therealmone.fireres.firemode.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.firemode.FireModeGuiceRunner;
import io.github.therealmone.fireres.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.firemode.service.FireModeService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.test.TestUtils.repeatTest;
import static io.github.therealmone.fireres.firemode.FireModeTestUtils.chooseNextFireModeType;

@RunWith(FireModeGuiceRunner.class)
public class FireModeWithTemperatureMaintainingRepeatingTest {

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FireModeService fireModeService;

    @Before
    public void setUpTemperatureMaintaining() {
        val sampleProperties = generationProperties.getSamples().get(0);

        val reportProperties = sampleProperties
                .getReportPropertiesByClass(FireModeProperties.class)
                .orElseThrow();

        reportProperties.setStandardTemperatureMaintaining(200);
    }

    @Test
    public void provideReportTest() {
        repeatTest(() -> {
            val sample = new Sample(generationProperties.getSamples().get(0));
            val report = fireModeService.createReport(sample);

            chooseNextFireModeType(report.getProperties());
        });
    }

}
