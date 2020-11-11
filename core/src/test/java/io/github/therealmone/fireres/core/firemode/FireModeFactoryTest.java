package io.github.therealmone.fireres.core.firemode;

import io.github.therealmone.fireres.core.common.config.GeneralProperties;
import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import lombok.val;
import org.junit.Test;

import java.util.List;

import static io.github.therealmone.fireres.core.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionNotLower;
import static io.github.therealmone.fireres.core.TestUtils.assertInterpolationPoints;
import static io.github.therealmone.fireres.core.TestUtils.assertMeanTemperatureInInterval;
import static io.github.therealmone.fireres.core.TestUtils.assertThermocouplesTemperaturesEqualsMean;
import static io.github.therealmone.fireres.core.TestUtils.defaultGenerationProperties;
import static io.github.therealmone.fireres.core.TestUtils.toPointList;
import static org.junit.Assert.assertEquals;

public class FireModeFactoryTest {

    @Test
    public void generateFurnaceTemperature() {
        val generatorFactory = new FireModeFactory(defaultGenerationProperties());

        val expectedValues = toPointList(List.of(
                42, 350, 446, 503, 545, 577, 604,
                627, 646, 664, 679, 694, 706, 718,
                729, 740, 749, 758, 767, 775, 782,
                790, 797, 803, 810, 816, 821, 827,
                833, 838, 843, 848, 852, 857, 861,
                866, 870, 874, 878, 882, 886, 889,
                893, 897, 900, 903, 907, 910, 913,
                916, 919, 922, 925, 928, 931, 933,
                936, 939, 941, 944, 946, 949, 951,
                954, 956, 958, 961, 963, 965, 967,
                969
        ));

        val standardTemp = generatorFactory.standardTemperature();
        val furnaceTemp = generatorFactory.furnaceTemperature(standardTemp);

        assertEquals(expectedValues, furnaceTemp.getValue());
    }

    @Test
    public void generateMaxAllowedTemperature() {
        val factory = new FireModeFactory(defaultGenerationProperties());

        val expectedFunction = toPointList(List.of(
                24, 378, 489, 554, 603,
                639, 670, 697, 719, 739,
                757, 740, 754, 767, 779,
                791, 801, 811, 821, 829,
                837, 846, 854, 860, 868,
                875, 880, 887, 893, 899,
                904, 868, 873, 878, 882,
                887, 891, 896, 900, 904,
                908, 911, 916, 920, 923,
                926, 930, 933, 937, 940,
                943, 946, 949, 952, 956,
                958, 961, 964, 966, 969,
                971, 974, 977, 980, 982,
                984, 987, 989, 991, 993,
                995
        ));

        val standardTemp = factory.standardTemperature();
        val maxAllowedTemp = factory.maxAllowedTemperature(standardTemp);

        assertEquals(expectedFunction, maxAllowedTemp.getValue());

        val smoothedFunction = maxAllowedTemp.getSmoothedValue();

        assertFunctionConstantlyGrowing(smoothedFunction);
        assertEquals(expectedFunction.size(), smoothedFunction.size());
        assertFunctionNotHigher(smoothedFunction, expectedFunction);
    }

    @Test
    public void generateMinAllowedTemperature() {
        val factory = new FireModeFactory(defaultGenerationProperties());

        val expectedFunction = toPointList(List.of(
                18, 280, 361, 410, 445,
                473, 496, 515, 531, 547,
                559, 606, 617, 627, 637,
                647, 655, 663, 671, 679,
                685, 692, 698, 704, 710,
                716, 720, 725, 731, 735,
                740, 786, 789, 794, 798,
                803, 807, 810, 814, 818,
                822, 825, 828, 832, 835,
                838, 842, 845, 847, 850,
                853, 856, 859, 862, 865,
                866, 869, 872, 874, 877,
                879, 882, 884, 886, 888,
                890, 893, 895, 897, 899,
                901
        ));

        val standardTemp = factory.standardTemperature();
        val minAllowedTemp = factory.minAllowedTemperature(standardTemp);

        assertEquals(expectedFunction, minAllowedTemp.getValue());

        val smoothedFunction = minAllowedTemp.getSmoothedValue();

        assertFunctionConstantlyGrowing(smoothedFunction);
        assertEquals(expectedFunction.size(), smoothedFunction.size());
        assertFunctionNotLower(smoothedFunction, expectedFunction);
    }

    @Test
    public void generateStandardTemperature() {
        val generator = new FireModeFactory(defaultGenerationProperties())
                .standardTemperature();

        val expectedNumbers = toPointList(List.of(
                21, 329, 425, 482, 524, 556, 583, 606,
                625, 643, 658, 673, 685, 697, 708, 719,
                728, 737, 746, 754, 761, 769, 776, 782,
                789, 795, 800, 806, 812, 817, 822, 827,
                831, 836, 840, 845, 849, 853, 857, 861,
                865, 868, 872, 876, 879, 882, 886, 889,
                892, 895, 898, 901, 904, 907, 910, 912,
                915, 918, 920, 923, 925, 928, 930, 933,
                935, 937, 940, 942, 944, 946, 948
        ));

        assertEquals(expectedNumbers, generator.getValue());
    }

    @Test
    public void generateStandardTemperatureWithOneNumber() {
        val generator = new FireModeFactory(GenerationProperties.builder()
                .general(GeneralProperties.builder()
                        .environmentTemperature(100)
                        .time(0)
                        .build())
                .build())
                .standardTemperature();

        val expectedNumbers = toPointList(List.of(100));

        assertEquals(expectedNumbers, generator.getValue());
    }

    @Test
    public void generateMeanTemperatureWithoutRandomPoints() {
        val props = defaultGenerationProperties();

        props.getSamples().get(0).getFireMode().getRandomPoints().setEnrichWithRandomPoints(false);

        val factory = new FireModeFactory(props);

        val standardTemp = factory.standardTemperature();
        val minTemp = factory.minAllowedTemperature(standardTemp);
        val maxTemp = factory.maxAllowedTemperature(standardTemp);
        val thermocoupleMeanTemp = factory.thermocoupleMeanTemperature(0, minTemp, maxTemp).getValue();

        assertInterpolationPoints(thermocoupleMeanTemp);
        assertFunctionConstantlyGrowing(thermocoupleMeanTemp);
    }

    @Test
    public void generateMeanTemperatureWithRandomPoints() {
        val factory = new FireModeFactory(defaultGenerationProperties());

        val standardTemp = factory.standardTemperature();
        val minTemp = factory.minAllowedTemperature(standardTemp);
        val maxTemp = factory.maxAllowedTemperature(standardTemp);
        val thermocoupleMeanTemp = factory.thermocoupleMeanTemperature(0, minTemp, maxTemp).getValue();

        assertInterpolationPoints(thermocoupleMeanTemp);
        assertFunctionConstantlyGrowing(thermocoupleMeanTemp);
    }

    @Test
    public void generateMeanTemperatureWithRandomPointsAndLowNewPointChance() {
        val props = defaultGenerationProperties();

        props.getSamples().get(0).getFireMode().getRandomPoints().setNewPointChance(0.1);

        val factory = new FireModeFactory(props);

        val standardTemp = factory.standardTemperature();
        val minTemp = factory.minAllowedTemperature(standardTemp);
        val maxTemp = factory.maxAllowedTemperature(standardTemp);
        val thermocoupleMeanTemp = factory.thermocoupleMeanTemperature(0, minTemp, maxTemp).getValue();

        assertInterpolationPoints(thermocoupleMeanTemp);
        assertFunctionConstantlyGrowing(thermocoupleMeanTemp);
    }

    @Test
    public void generateThermocouplesTemperatures() {
        val factory = new FireModeFactory(defaultGenerationProperties());

        val standardTemp = factory.standardTemperature();
        val minTemp = factory.minAllowedTemperature(standardTemp);
        val maxTemp = factory.maxAllowedTemperature(standardTemp);

        val meanTemp = factory.thermocoupleMeanTemperature(0, minTemp, maxTemp);

        assertMeanTemperatureInInterval(meanTemp.getValue(), minTemp.getValue(), maxTemp.getValue());
        assertFunctionConstantlyGrowing(meanTemp.getValue());

        val thermocouplesTemp = factory.thermocouplesTemperatures(
                minTemp, maxTemp, meanTemp, 0);

        assertThermocouplesTemperaturesEqualsMean(thermocouplesTemp, meanTemp);
        thermocouplesTemp.forEach(t -> assertFunctionConstantlyGrowing(t.getValue()));
    }
}
