package io.github.therealmone.fireres.core.pressure;

import io.github.therealmone.fireres.core.TestGenerationProperties;
import io.github.therealmone.fireres.core.common.model.DoublePoint;
import lombok.val;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.TestUtils.*;

public class ExcessPressureFactoryTest {

    @Test
    public void generateMinAllowedPressure() {
        val props = new TestGenerationProperties();
        val factory = new ExcessPressureFactory(props);

        val minAllowedPressure = factory.minAllowedPressure();

        assertFunctionIsConstant(
                -props.getGeneral().getExcessPressure().getDelta(),
                minAllowedPressure.getValue());
    }

    @Test
    public void generateMaxAllowedPressure() {
        val props = new TestGenerationProperties();
        val factory = new ExcessPressureFactory(props);

        val maxAllowedPressure = factory.maxAllowedPressure();

        assertFunctionIsConstant(
                props.getGeneral().getExcessPressure().getDelta(),
                maxAllowedPressure.getValue());
    }

    @Test
    public void generatePressure() {
        val props = new TestGenerationProperties();
        val factory = new ExcessPressureFactory(props);

        val minAllowedPressure = factory.minAllowedPressure();
        val maxAllowedPressure = factory.maxAllowedPressure();
        val pressure = factory.excessPressure(minAllowedPressure, maxAllowedPressure);

        val upperBound = IntStream.range(0, props.getGeneral().getTime())
                .mapToObj(t -> new DoublePoint(t, props.getGeneral().getExcessPressure().getDelta()))
                .collect(Collectors.toList());

        val lowerBound = IntStream.range(0, props.getGeneral().getTime())
                .mapToObj(t -> new DoublePoint(t, -props.getGeneral().getExcessPressure().getDelta()))
                .collect(Collectors.toList());

        assertFunctionNotHigher(pressure.getValue(), upperBound);
        assertFunctionNotLower(pressure.getValue(), lowerBound);
    }
}
