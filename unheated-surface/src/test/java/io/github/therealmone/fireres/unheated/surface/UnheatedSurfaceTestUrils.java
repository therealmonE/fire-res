package io.github.therealmone.fireres.unheated.surface;

import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

import static io.github.therealmone.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.unheated.surface.TestGenerationProperties.TIME;
import static org.junit.Assert.assertEquals;

public class UnheatedSurfaceTestUrils {

    public static void assertUnheatedSurfaceReportIsValid(UnheatedSurfaceReport report) {
        assertUnheatedSurfaceGroup(report.getFirstGroup());
        assertUnheatedSurfaceGroup(report.getSecondGroup());
        assertUnheatedSurfaceGroup(report.getThirdGroup());
    }

    private static void assertUnheatedSurfaceGroup(UnheatedSurfaceGroup group) {
        val meanBound = group.getMeanBound();
        val thermocoupleBound = group.getThermocoupleBound();
        val meanTemp = group.getMeanTemperature();

        assertFunctionConstantlyGrowing(meanTemp.getValue());
        assertFunctionNotLower(meanTemp.getValue(), constantFunction(0, TIME).getValue());

        if (meanBound != null) {
            assertFunctionNotHigher(meanTemp.getValue(), meanBound.getValue());
        } else {
            assertFunctionNotHigher(meanTemp.getValue(), thermocoupleBound.getValue());
        }

        val thermocouplesTemps = group.getThermocoupleTemperatures();

        assertChildTemperaturesEqualsMean(thermocouplesTemps, meanTemp);

        thermocouplesTemps.forEach(thermocouplesTemp -> {

            assertEquals(TIME, thermocouplesTemp.getValue().size());

            assertFunctionConstantlyGrowing(thermocouplesTemp.getValue());
            assertFunctionNotLower(thermocouplesTemp.getValue(), constantFunction(0, TIME).getValue());
            assertFunctionNotHigher(thermocouplesTemp.getValue(), thermocoupleBound.getValue());
        });
    }
}
