package io.github.therealmone.fireres.core.factory;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.generator.FurnaceTempGenerator;
import io.github.therealmone.fireres.core.generator.StandardTempGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class NumberSequenceGeneratorFactory {

    private final GenerationProperties generationProperties;

    public StandardTempGenerator standardTempGenerator() {
        return new StandardTempGenerator(
                generationProperties.getT0(),
                generationProperties.getTime());
    }

    public FurnaceTempGenerator furnaceTempGenerator(List<Integer> standardTemp) {
        return new FurnaceTempGenerator(
                generationProperties.getT0(),
                standardTemp);
    }

}
