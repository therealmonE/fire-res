package io.github.therealmone.fireres.core.generator.firemode;

import io.github.therealmone.fireres.core.firemode.FireModeFactory;
import lombok.val;
import org.junit.Test;

import java.util.List;

import static io.github.therealmone.fireres.core.TestUtils.defaultGenerationProperties;
import static io.github.therealmone.fireres.core.TestUtils.toPointList;
import static org.junit.Assert.assertEquals;

public class FurnaceTempGeneratorTest {

    @Test
    public void generateTest() {
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

}