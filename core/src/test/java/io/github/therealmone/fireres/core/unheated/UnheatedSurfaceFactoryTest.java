package io.github.therealmone.fireres.core.unheated;

import lombok.val;
import org.junit.Test;

import static io.github.therealmone.fireres.core.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionIsConstant;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.core.TestUtils.assertThermocouplesTemperaturesEqualsMean;
import static io.github.therealmone.fireres.core.TestUtils.defaultGenerationProperties;
import static io.github.therealmone.fireres.core.utils.FunctionUtils.*;

public class UnheatedSurfaceFactoryTest {

    @Test
    public void generateMeanBound() {
        val props = defaultGenerationProperties();
        val factory = new UnheatedSurfaceFactory(props);

        val meanBound = factory.meanBound();

        assertFunctionIsConstant(
                140 + props.getGeneral().getEnvironmentTemperature(),
                meanBound.getValue());
    }

    @Test
    public void generateThermocoupleBound() {
        val props = defaultGenerationProperties();
        val factory = new UnheatedSurfaceFactory(props);

        val thermocoupleBound = factory.thermocoupleBound();

        assertFunctionIsConstant(
                180 + props.getGeneral().getEnvironmentTemperature(),
                thermocoupleBound.getValue());
    }

    @Test
    public void generateFirstGroup() {
        val props = defaultGenerationProperties();
        val factory = new UnheatedSurfaceFactory(props);

        val thermocoupleBound = factory.thermocoupleBound();
        val meanBound = factory.meanBound();

        val firstGroup = factory.firstThermocoupleGroup(0, meanBound, thermocoupleBound);

        val meanTemperature = firstGroup.getMeanTemperature();

        assertFunctionConstantlyGrowing(meanTemperature.getValue());
        assertFunctionNotHigher(meanTemperature.getValue(), meanBound.getValue());
        assertFunctionNotLower(
                meanTemperature.getValue(),
                constantFunction(props.getGeneral().getTime(), 0).getValue());

        val thermocouples = firstGroup.getThermocoupleTemperatures();

        assertThermocouplesTemperaturesEqualsMean(thermocouples, meanTemperature);

        for (val thermocouple : thermocouples) {
            assertFunctionConstantlyGrowing(thermocouple.getValue());
            assertFunctionNotHigher(thermocouple.getValue(), thermocoupleBound.getValue());
            assertFunctionNotLower(
                    thermocouple.getValue(),
                    constantFunction(props.getGeneral().getTime(), 0).getValue());
        }
    }

    @Test
    public void generateSecondGroup() {
        val props = defaultGenerationProperties();
        val factory = new UnheatedSurfaceFactory(props);

        val thermocoupleBound = factory.thermocoupleBound();
        val meanBound = factory.meanBound();

        val secondGroup = factory.secondThermocoupleGroup(0, meanBound, thermocoupleBound);

        val meanTemperature = secondGroup.getMeanTemperature();

        assertFunctionConstantlyGrowing(meanTemperature.getValue());
        assertFunctionNotHigher(meanTemperature.getValue(), meanBound.getValue());
        assertFunctionNotLower(
                meanTemperature.getValue(),
                constantFunction(props.getGeneral().getTime(), 0).getValue());

        val thermocouples = secondGroup.getThermocoupleTemperatures();

        assertThermocouplesTemperaturesEqualsMean(thermocouples, meanTemperature);

        for (val thermocouple : thermocouples) {
            assertFunctionConstantlyGrowing(thermocouple.getValue());
            assertFunctionNotHigher(thermocouple.getValue(), thermocoupleBound.getValue());
            assertFunctionNotLower(
                    thermocouple.getValue(),
                    constantFunction(props.getGeneral().getTime(), 0).getValue());
        }
    }

    @Test
    public void generateThirdGroup() {
        val props = defaultGenerationProperties();
        val factory = new UnheatedSurfaceFactory(props);


        val thirdGroup = factory.thirdThermocoupleGroup(0);

        val meanTemperature = thirdGroup.getMeanTemperature();
        val thermocoupleBound = thirdGroup.getThermocoupleBound();

        assertFunctionIsConstant(
                props.getSamples().get(0).getUnheatedSurface().getThirdGroup().getBound(),
                thermocoupleBound.getValue());
        assertFunctionConstantlyGrowing(meanTemperature.getValue());
        assertFunctionNotHigher(meanTemperature.getValue(), thermocoupleBound.getValue());
        assertFunctionNotLower(
                meanTemperature.getValue(),
                constantFunction(props.getGeneral().getTime(), 0).getValue());

        val thermocouples = thirdGroup.getThermocoupleTemperatures();


        assertThermocouplesTemperaturesEqualsMean(thermocouples, meanTemperature);

        for (val thermocouple : thermocouples) {
            assertFunctionConstantlyGrowing(thermocouple.getValue());
            assertFunctionNotHigher(thermocouple.getValue(), thermocoupleBound.getValue());
            assertFunctionNotLower(
                    thermocouple.getValue(),
                    constantFunction(props.getGeneral().getTime(), 0).getValue());
        }
    }

}