package io.github.therealmone.fireres.core.generator.firemode;

import io.github.therealmone.fireres.core.firemode.FireModeFactory;
import lombok.val;
import org.junit.Test;

import java.util.List;

import static io.github.therealmone.fireres.core.TestUtils.assertFunctionConstantlyGrowing;
import static io.github.therealmone.fireres.core.TestUtils.assertFunctionNotHigher;
import static io.github.therealmone.fireres.core.TestUtils.defaultGenerationProperties;
import static io.github.therealmone.fireres.core.TestUtils.toPointList;
import static org.junit.Assert.assertEquals;

public class MaxAllowedTempGeneratorTest {

    @Test
    public void generate() {
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

}