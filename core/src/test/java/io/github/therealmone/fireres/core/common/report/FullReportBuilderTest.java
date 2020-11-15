package io.github.therealmone.fireres.core.common.report;

import io.github.therealmone.fireres.core.TestGenerationProperties;
import lombok.val;
import org.junit.Test;

import static io.github.therealmone.fireres.core.TestUtils.assertExcessPressure;
import static io.github.therealmone.fireres.core.TestUtils.assertFireMode;
import static io.github.therealmone.fireres.core.TestUtils.assertUnheatedSurface;
import static java.util.Collections.emptyList;

public class FullReportBuilderTest {

    private static final Integer CYCLES = 1000;

    @Test
    public void buildMultipleTimes() {
        for (int i = 0; i < CYCLES; i++) {
            build();
        }
    }

    @Test
    public void build() {
        val report = new FullReportBuilder().build(new TestGenerationProperties());

        assertFireMode(report);
        assertExcessPressure(report);
        assertUnheatedSurface(report);
    }

    @Test
    public void buildWithoutInterpolationPoints() {
        val props = new TestGenerationProperties();

        props.getSamples().get(0).getFireMode().setInterpolationPoints(emptyList());

        val report = new FullReportBuilder().build(props);

        assertFireMode(report);
        assertExcessPressure(report);
        assertUnheatedSurface(report);
    }
}