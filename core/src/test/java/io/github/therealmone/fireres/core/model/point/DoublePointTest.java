package io.github.therealmone.fireres.core.model.point;

import io.github.therealmone.fireres.core.common.model.DoublePoint;
import lombok.val;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DoublePointTest {

    @Test
    public void normalizeWithPositiveShift() {
        val doublePoint = new DoublePoint(0,1.2345);

        assertEquals(1234, (int) doublePoint.normalize(3).getValue());
    }

    @Test
    public void normalizeWithNegativeShift() {
        val doublePoint = new DoublePoint(0,1234.5);

        assertEquals(1, (int) doublePoint.normalize(-3).getValue());
    }

    @Test
    public void normalizeWithPositiveShiftAndIntValue() {
        val doublePoint = new DoublePoint(0,12345.0);

        assertEquals(12345000, (int) doublePoint.normalize(3).getValue());
    }

    @Test
    public void normalizeWithNegativeShiftAndLowValue() {
        val doublePoint = new DoublePoint(0,1.2345);

        assertEquals(0, (int) doublePoint.normalize(-3).getValue());
    }

}