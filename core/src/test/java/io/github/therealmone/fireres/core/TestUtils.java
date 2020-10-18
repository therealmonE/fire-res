package io.github.therealmone.fireres.core;

import lombok.val;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static void assertFunctionConstantlyGrowing(List<Integer> function) {
        for (int i = 1; i < function.size(); i++) {
            val prevValue = function.get(i - 1);
            val value = function.get(i);
            assertTrue("Comparing " + prevValue + " and " + value, value > prevValue);
        }
    }

}
