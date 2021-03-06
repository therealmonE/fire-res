package io.github.therealmone.fireres.core.utils;

import io.github.therealmone.fireres.core.model.BoundShift;
import io.github.therealmone.fireres.core.model.DoublePoint;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.model.IntegerPointSequence;
import io.github.therealmone.fireres.core.model.Point;
import lombok.val;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunctionUtils {

    public static List<Pair<IntegerPoint, IntegerPoint>> asIntervals(List<IntegerPoint> function) {
        if (function.size() <= 1) {
            throw new IllegalArgumentException();
        }

        return IntStream.range(1, function.size())
                .mapToObj(i -> new Pair<>(function.get(i - 1), function.get(i)))
                .collect(Collectors.toList());
    }

    public static IntegerPointSequence constantFunction(Integer time, Integer value) {
        return new IntegerPointSequence(IntStream.range(0, time)
                .mapToObj(t -> new IntegerPoint(t, value))
                .collect(Collectors.toList()));
    }

    public static List<IntegerPoint> calculateShiftedIntegerPoints(List<IntegerPoint> original,
                                                                   BoundShift<IntegerPoint> shift) {

        return calculateShiftedPoints(
                original, shift,
                (p1, p2) -> p1.getValue() + p2.getValue(),
                IntegerPoint::new);
    }

    public static List<DoublePoint> calculateShiftedDoublePoints(List<DoublePoint> original,
                                                                 BoundShift<DoublePoint> shift) {

        return calculateShiftedPoints(
                original, shift,
                (p1, p2) -> p1.getValue() + p2.getValue(),
                DoublePoint::new);
    }

    public static <N extends Number, P extends Point<N>>
    List<P> calculateShiftedPoints(List<P> original,
                                   BoundShift<P> shift,
                                   BiFunction<P, P, N> shiftedValueCalculator,
                                   BiFunction<Integer, N, P> newPointCreator) {

        val shiftedPoints = new ArrayList<P>();

        for (P originalPoint : original) {
            val pointShift = lookupShiftForPoint(originalPoint, shift);

            if (pointShift.isPresent()) {
                val shiftedValue = shiftedValueCalculator.apply(originalPoint, pointShift.get());
                shiftedPoints.add(newPointCreator.apply(originalPoint.getTime(), shiftedValue));
            } else {
                shiftedPoints.add(newPointCreator.apply(originalPoint.getTime(), originalPoint.getValue()));
            }
        }

        return shiftedPoints;
    }

    private static <P extends Point<?>> Optional<P> lookupShiftForPoint(P originalPoint, BoundShift<P> shift) {
        return shift.getPoints().stream()
                .filter(s -> s.getTime() <= originalPoint.getTime())
                .min(Comparator.comparingInt(s -> (originalPoint.getTime() - s.getTime())));
    }

}
