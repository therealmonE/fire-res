package io.github.therealmone.fireres.core.generator.firemode;

import io.github.therealmone.fireres.core.factory.ExcessPressureFactory;
import io.github.therealmone.fireres.core.model.point.DoublePoint;
import lombok.val;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.therealmone.fireres.core.TestUtils.*;

public class ExcessPressureGeneratorTest {

    @Test
    public void generateTest() {
        val props = defaultGenerationProperties();
        val factory = new ExcessPressureFactory(props);

        val pressure = factory.excessPressure();

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
