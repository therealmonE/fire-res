package io.github.therealmone.fireres.unheated.surface.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.unheated.surface.GuiceRunner;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceSample;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.unheated.surface.TestGenerationProperties.ENVIRONMENT_TEMPERATURE;
import static io.github.therealmone.fireres.unheated.surface.TestGenerationProperties.SECOND_GROUP_BOUND;
import static io.github.therealmone.fireres.unheated.surface.TestGenerationProperties.THIRD_GROUP_BOUND;
import static io.github.therealmone.fireres.unheated.surface.TestGenerationProperties.TIME;
import static io.github.therealmone.fireres.unheated.surface.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.unheated.surface.TestUtils.assertFunctionIsConstant;
import static io.github.therealmone.fireres.unheated.surface.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.unheated.surface.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.unheated.surface.TestUtils.assertThermocouplesTemperaturesEqualsMean;
import static io.github.therealmone.fireres.unheated.surface.TestUtils.assertUnheatedSurfaceReportIsValid;
import static org.junit.Assert.assertNull;

@RunWith(GuiceRunner.class)
public class UnheatedSurfaceReportTest {

    @Inject
    private UnheatedSurfaceReportProvider reportProvider;

    @Test
    public void generateMeanBound() {
        val report = reportProvider.get();

        for (UnheatedSurfaceSample sample : report.getSamples()) {

            //first group
            {
                val meanBound = sample.getFirstGroup().getMeanBound();

                assertFunctionIsConstant(
                        140 + ENVIRONMENT_TEMPERATURE,
                        meanBound.getValue());
            }

            //second group
            {
                val meanBound = sample.getSecondGroup().getMeanBound();
                assertNull(meanBound);
            }

            //third group
            {
                val meanBound = sample.getThirdGroup().getMeanBound();
                assertNull(meanBound);
            }

        }
    }

    @Test
    public void generateThermocoupleBound() {
        val report = reportProvider.get();

        for (UnheatedSurfaceSample sample : report.getSamples()) {

            //first group
            {
                val thermocoupleBound = sample.getFirstGroup().getThermocoupleBound();

                assertFunctionIsConstant(
                        180 + ENVIRONMENT_TEMPERATURE,
                        thermocoupleBound.getValue());
            }

            //second group
            {
                val thermocoupleBound = sample.getSecondGroup().getThermocoupleBound();

                assertFunctionIsConstant(
                        SECOND_GROUP_BOUND,
                        thermocoupleBound.getValue());
            }

            //third group
            {
                val thermocoupleBound = sample.getThirdGroup().getThermocoupleBound();

                assertFunctionIsConstant(
                        THIRD_GROUP_BOUND,
                        thermocoupleBound.getValue());
            }

        }
    }

    @Test
    public void generateFirstGroup() {
        val report = reportProvider.get();

        for (UnheatedSurfaceSample sample : report.getSamples()) {
            val firstGroup = sample.getFirstGroup();

            val thermocoupleBound = firstGroup.getThermocoupleBound();
            val meanBound = firstGroup.getMeanBound();

            val meanTemperature = firstGroup.getMeanTemperature();

            assertFunctionConstantlyGrowing(meanTemperature.getValue());
            assertFunctionNotHigher(meanTemperature.getValue(), meanBound.getValue());
            assertFunctionNotLower(
                    meanTemperature.getValue(),
                    constantFunction(TIME, 0).getValue());

            val thermocouples = firstGroup.getThermocoupleTemperatures();

            assertThermocouplesTemperaturesEqualsMean(thermocouples, meanTemperature);

            for (val thermocouple : thermocouples) {
                assertFunctionConstantlyGrowing(thermocouple.getValue());
                assertFunctionNotHigher(thermocouple.getValue(), thermocoupleBound.getValue());
                assertFunctionNotLower(
                        thermocouple.getValue(),
                        constantFunction(TIME, 0).getValue());
            }
        }
    }

    @Test
    public void generateSecondGroup() {
        val report = reportProvider.get();

        for (UnheatedSurfaceSample sample : report.getSamples()) {
            val secondGroup = sample.getSecondGroup();

            val meanTemperature = secondGroup.getMeanTemperature();
            val thermocoupleBound = secondGroup.getThermocoupleBound();

            assertFunctionIsConstant(
                    SECOND_GROUP_BOUND,
                    thermocoupleBound.getValue());
            assertFunctionConstantlyGrowing(meanTemperature.getValue());
            assertFunctionNotHigher(meanTemperature.getValue(), thermocoupleBound.getValue());
            assertFunctionNotLower(
                    meanTemperature.getValue(),
                    constantFunction(TIME, 0).getValue());

            val thermocouples = secondGroup.getThermocoupleTemperatures();

            assertThermocouplesTemperaturesEqualsMean(thermocouples, meanTemperature);

            for (val thermocouple : thermocouples) {
                assertFunctionConstantlyGrowing(thermocouple.getValue());
                assertFunctionNotHigher(thermocouple.getValue(), thermocoupleBound.getValue());
                assertFunctionNotLower(
                        thermocouple.getValue(),
                        constantFunction(TIME, 0).getValue());
            }
        }

    }

    @Test
    public void generateThirdGroup() {
        val report = reportProvider.get();

        for (UnheatedSurfaceSample sample : report.getSamples()) {
            val thirdGroup = sample.getThirdGroup();

            val meanTemperature = thirdGroup.getMeanTemperature();
            val thermocoupleBound = thirdGroup.getThermocoupleBound();

            assertFunctionIsConstant(
                    THIRD_GROUP_BOUND,
                    thermocoupleBound.getValue());
            assertFunctionConstantlyGrowing(meanTemperature.getValue());
            assertFunctionNotHigher(meanTemperature.getValue(), thermocoupleBound.getValue());
            assertFunctionNotLower(
                    meanTemperature.getValue(),
                    constantFunction(TIME, 0).getValue());

            val thermocouples = thirdGroup.getThermocoupleTemperatures();

            assertThermocouplesTemperaturesEqualsMean(thermocouples, meanTemperature);

            for (val thermocouple : thermocouples) {
                assertFunctionConstantlyGrowing(thermocouple.getValue());
                assertFunctionNotHigher(thermocouple.getValue(), thermocoupleBound.getValue());
                assertFunctionNotLower(
                        thermocouple.getValue(),
                        constantFunction(TIME, 0).getValue());
            }
        }
    }

    @Test
    public void provideReportTest() {
        val report = reportProvider.get();

        assertUnheatedSurfaceReportIsValid(report);
    }

}