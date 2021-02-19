package io.github.therealmone.fireres.unheated.surface.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.unheated.surface.UnheatedSurfaceGuiceRunner;
import io.github.therealmone.fireres.unheated.surface.service.UnheatedSurfaceService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.therealmone.fireres.core.test.TestUtils.assertChildTemperaturesEqualsMean;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionIsConstant;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.test.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.core.utils.FunctionUtils.constantFunction;
import static io.github.therealmone.fireres.unheated.surface.TestGenerationProperties.ENVIRONMENT_TEMPERATURE;
import static io.github.therealmone.fireres.unheated.surface.TestGenerationProperties.SECOND_GROUP_BOUND;
import static io.github.therealmone.fireres.unheated.surface.TestGenerationProperties.THIRD_GROUP_BOUND;
import static io.github.therealmone.fireres.unheated.surface.TestGenerationProperties.TIME;
import static org.junit.Assert.assertNull;

@RunWith(UnheatedSurfaceGuiceRunner.class)
public class UnheatedSurfaceReportTest {

    @Inject
    private UnheatedSurfaceService unheatedSurfaceService;

    @Inject
    private GenerationProperties generationProperties;

    @Test
    public void generateMeanBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        //first group
        {
            val meanBound = report.getFirstGroup().getMeanBound();

            assertFunctionIsConstant(
                    140 + ENVIRONMENT_TEMPERATURE,
                    meanBound.getValue());
        }

        //second group
        {
            val meanBound = report.getSecondGroup().getMeanBound();
            assertNull(meanBound);
        }

        //third group
        {
            val meanBound = report.getThirdGroup().getMeanBound();
            assertNull(meanBound);
        }
    }

    @Test
    public void generateThermocoupleBound() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        //first group
        {
            val thermocoupleBound = report.getFirstGroup().getThermocoupleBound();

            assertFunctionIsConstant(
                    180 + ENVIRONMENT_TEMPERATURE,
                    thermocoupleBound.getValue());
        }

        //second group
        {
            val thermocoupleBound = report.getSecondGroup().getThermocoupleBound();

            assertFunctionIsConstant(
                    SECOND_GROUP_BOUND,
                    thermocoupleBound.getValue());
        }

        //third group
        {
            val thermocoupleBound = report.getThirdGroup().getThermocoupleBound();

            assertFunctionIsConstant(
                    THIRD_GROUP_BOUND,
                    thermocoupleBound.getValue());
        }

    }

    @Test
    public void generateFirstGroup() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val firstGroup = report.getFirstGroup();

        val thermocoupleBound = firstGroup.getThermocoupleBound();
        val meanBound = firstGroup.getMeanBound();

        val meanTemperature = firstGroup.getMeanTemperature();

        assertFunctionConstantlyGrowing(meanTemperature.getValue());
        assertFunctionNotHigher(meanTemperature.getValue(), meanBound.getValue());
        assertFunctionNotLower(
                meanTemperature.getValue(),
                constantFunction(TIME, 0).getValue());

        val thermocouples = firstGroup.getThermocoupleTemperatures();

        assertChildTemperaturesEqualsMean(thermocouples, meanTemperature);

        for (val thermocouple : thermocouples) {
            assertFunctionConstantlyGrowing(thermocouple.getValue());
            assertFunctionNotHigher(thermocouple.getValue(), thermocoupleBound.getValue());
            assertFunctionNotLower(
                    thermocouple.getValue(),
                    constantFunction(TIME, 0).getValue());
        }
    }

    @Test
    public void generateSecondGroup() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val secondGroup = report.getSecondGroup();

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

        assertChildTemperaturesEqualsMean(thermocouples, meanTemperature);

        for (val thermocouple : thermocouples) {
            assertFunctionConstantlyGrowing(thermocouple.getValue());
            assertFunctionNotHigher(thermocouple.getValue(), thermocoupleBound.getValue());
            assertFunctionNotLower(
                    thermocouple.getValue(),
                    constantFunction(TIME, 0).getValue());
        }

    }

    @Test
    public void generateThirdGroup() {
        val sample = new Sample(generationProperties.getSamples().get(0));
        val report = unheatedSurfaceService.createReport(sample);

        val thirdGroup = report.getThirdGroup();

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

        assertChildTemperaturesEqualsMean(thermocouples, meanTemperature);

        for (val thermocouple : thermocouples) {
            assertFunctionConstantlyGrowing(thermocouple.getValue());
            assertFunctionNotHigher(thermocouple.getValue(), thermocoupleBound.getValue());
            assertFunctionNotLower(
                    thermocouple.getValue(),
                    constantFunction(TIME, 0).getValue());
        }
    }

}