package io.github.therealmone.fireres.core.report;

import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.factory.PointSequenceGeneratorFactory;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.validation.Validator;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReportBuilder {

    public static Report build(GenerationProperties properties) {
        Validator.validateGenerationProperties(properties);

        val factory = new PointSequenceGeneratorFactory(properties);

        val standardTemp = factory.standardTempGenerator().generate();
        val maxAllowedTemp = factory.maxAllowedTempGenerator(standardTemp).generate();
        val minAllowedTemp = factory.minAllowedTempGenerator(standardTemp).generate();
        val furnaceTemp = factory.furnaceTempGenerator(standardTemp).generate();

        List<Sample> samples = IntStream.range(0, properties.getSamples().size())
                .mapToObj(i -> {
                    val meanTemp = factory.thermocoupleMeanGenerator(i).generate();

                    return Sample.builder()
                            .thermocoupleMeanTemperature(meanTemp)
                            .thermocoupleTemperatures(factory.
                                    thermocouplesTempGenerator(minAllowedTemp, maxAllowedTemp, meanTemp, i)
                                    .generate())
                            .build();
                })
                .collect(Collectors.toList());

        return Report.builder()
                .time(properties.getTime())
                .environmentTemperature(properties.getTemperature().getEnvironmentTemperature())
                .furnaceTemperature(furnaceTemp)
                .minAllowedTemperature(minAllowedTemp)
                .maxAllowedTemperature(maxAllowedTemp)
                .standardTemperature(standardTemp)
                .samples(samples)
                .build();
    }

}
