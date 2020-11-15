package io.github.therealmone.fireres.excel;

import io.github.therealmone.fireres.core.common.config.GeneralProperties;
import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.core.common.config.RandomPointsProperties;
import io.github.therealmone.fireres.core.common.config.SampleProperties;
import io.github.therealmone.fireres.core.firemode.config.FireModeProperties;
import io.github.therealmone.fireres.core.common.model.IntegerPoint;
import io.github.therealmone.fireres.core.pressure.config.ExcessPressureProperties;
import io.github.therealmone.fireres.core.unheated.config.UnheatedSurfaceGroupProperties;
import io.github.therealmone.fireres.core.unheated.config.UnheatedSurfaceProperties;
import io.github.therealmone.fireres.core.unheated.config.UnheatedSurfaceSecondaryGroupProperties;
import lombok.val;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReportConstructorTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static final List<IntegerPoint> INTERPOLATION_POINTS = new ArrayList<>() {{
        add(new IntegerPoint(0, 21));
        add(new IntegerPoint(1, 306));
        add(new IntegerPoint(2, 392));
        add(new IntegerPoint(3, 480));
        add(new IntegerPoint(8, 615));
        add(new IntegerPoint(18, 749));
        add(new IntegerPoint(21, 789));
        add(new IntegerPoint(26, 822));
        add(new IntegerPoint(48, 898));
        add(new IntegerPoint(49, 901));
        add(new IntegerPoint(70, 943));
    }};

    @Test
    public void construct() throws IOException {
        val file = temporaryFolder.newFile("test.xls");

        val props = GenerationProperties.builder()
                .general(GeneralProperties.builder()
                        .environmentTemperature(21)
                        .time(71)
                        .excessPressure(ExcessPressureProperties.builder()
                                .basePressure(10.0)
                                .delta(2.0)
                                .build())
                        .build())
                .samples(List.of(SampleProperties.builder()
                        .fireMode(FireModeProperties.builder()
                                .randomPoints(RandomPointsProperties.builder()
                                        .enrichWithRandomPoints(true)
                                        .newPointChance(1.0)
                                        .build())
                                .interpolationPoints(INTERPOLATION_POINTS)
                                .thermocoupleCount(6)
                                .build())
                        .unheatedSurface(UnheatedSurfaceProperties.builder()
                                .firstGroup(UnheatedSurfaceGroupProperties.builder()
                                        .thermocoupleCount(5)
                                        .build())
                                .secondGroup(UnheatedSurfaceSecondaryGroupProperties.builder()
                                        .thermocoupleCount(5)
                                        .bound(300)
                                        .build())
                                .thirdGroup(UnheatedSurfaceSecondaryGroupProperties.builder()
                                        .thermocoupleCount(5)
                                        .bound(300)
                                        .build())
                                .build())
                        .build()))
                .build();

        val constructor = new ExcelReportConstructor(props);

        constructor.construct(file);
    }

}