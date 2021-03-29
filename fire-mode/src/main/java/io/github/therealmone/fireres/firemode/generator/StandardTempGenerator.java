package io.github.therealmone.fireres.firemode.generator;

import io.github.therealmone.fireres.core.generator.PointSequenceGenerator;
import io.github.therealmone.fireres.firemode.model.StandardTemperature;
import io.github.therealmone.fireres.core.model.IntegerPoint;
import io.github.therealmone.fireres.firemode.model.FireModeType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;

import static io.github.therealmone.fireres.firemode.utils.TemperatureMaintainingUtils.generateMaintainedTemperature;
import static io.github.therealmone.fireres.firemode.utils.TemperatureMaintainingUtils.shouldMaintainTemperature;

@RequiredArgsConstructor
@Slf4j
@Builder
public class StandardTempGenerator implements PointSequenceGenerator<StandardTemperature> {

    private final Integer t0;
    private final Integer time;
    private final FireModeType fireModeType;
    private final Integer standardTemperatureMaintaining;

    @Override
    public StandardTemperature generate() {
        val standardTemp = new ArrayList<IntegerPoint>();

        Integer temperatureMaintainingTime = null;
        for (int t = 1; t < time; t++) {
            val functionValue = fireModeType.getFunction().apply(t);

            if (standardTemperatureMaintaining != null && shouldMaintainTemperature(functionValue, standardTemperatureMaintaining)) {
                standardTemp.addAll(generateMaintainedTemperature(standardTemperatureMaintaining, t, time));
                temperatureMaintainingTime = t;

                break;
            } else {
                standardTemp.add(new IntegerPoint(t, functionValue));
            }
        }

        standardTemp.add(0, new IntegerPoint(0, t0));

        return new StandardTemperature(standardTemp, temperatureMaintainingTime);
    }

}
