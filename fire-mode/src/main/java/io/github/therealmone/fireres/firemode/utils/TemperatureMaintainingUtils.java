package io.github.therealmone.fireres.firemode.utils;

import io.github.therealmone.fireres.core.generator.Noise;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.core.utils.InterpolationUtils;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;

@UtilityClass
public class TemperatureMaintainingUtils {

    private static final Double TEMPERATURE_MAINTAINING_COEFFICIENT = 0.05;
    private static final Integer NOISE_POINTS_INTERVAL = 5;

    public static boolean shouldMaintainTemperature(Integer temperature, Integer temperatureMaintaining) {
        val min = temperatureMaintaining * (1 - TEMPERATURE_MAINTAINING_COEFFICIENT);

        return temperature >= min;
    }

    public static List<IntegerPoint> generateMaintainedTemperature(Integer temperatureMaintaining,
                                                                   Integer tStart, Integer tEnd) {

        val min = temperatureMaintaining * (1 - TEMPERATURE_MAINTAINING_COEFFICIENT);
        val max = temperatureMaintaining * (1 + TEMPERATURE_MAINTAINING_COEFFICIENT);

        val noise = generateNoise(tStart, tEnd, min, max);

        return generateMaintainedTemperatureByNoise(noise);
    }

    private static ArrayList<IntegerPoint> generateNoise(Integer tStart, Integer tEnd, double min, double max) {
        val seed = generateValueInInterval(0, Integer.MAX_VALUE - 1);

        val noise = new ArrayList<IntegerPoint>();

        for (int t = tStart; t < tEnd; t++) {
            val noiseValue = Noise.noise(min, max, t, 1d, seed);

            noise.add(new IntegerPoint(t, (int) Math.round(noiseValue)));
        }

        return noise;
    }

    private static List<IntegerPoint> generateMaintainedTemperatureByNoise(List<IntegerPoint> noise) {
        val interpolationPoints = new ArrayList<IntegerPoint>();

        interpolationPoints.add(noise.get(0));

        for (int i = 1; i < noise.size() - 1; i += NOISE_POINTS_INTERVAL) {
            interpolationPoints.add(noise.get(i));
        }

        interpolationPoints.add(noise.get(noise.size() - 1));

        return InterpolationUtils.interpolate(interpolationPoints);
    }

}
