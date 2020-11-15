package io.github.therealmone.fireres.core.unheated.report;

import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.core.common.report.ReportBuilder;
import io.github.therealmone.fireres.core.unheated.UnheatedSurfaceFactory;
import io.github.therealmone.fireres.core.unheated.model.UnheatedSurfaceSample;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class UnheatedSurfaceReportBuilder implements ReportBuilder<UnheatedSurfaceReport> {

    @Override
    public UnheatedSurfaceReport build(GenerationProperties properties) {
        log.info("Building unheated surface report");
        val factory = new UnheatedSurfaceFactory(properties);

        val meanBound = factory.meanBound();
        val thermocoupleBound = factory.thermocoupleBound();

        val samples = IntStream.range(0, properties.getSamples().size())
                .mapToObj(sample -> UnheatedSurfaceSample.builder()
                        .firstGroup(factory.firstThermocoupleGroup(sample, meanBound, thermocoupleBound))
                        .secondGroup(factory.secondThermocoupleGroup(sample, meanBound, thermocoupleBound))
                        .thirdGroup(factory.thirdThermocoupleGroup(sample))
                        .build())
                .collect(Collectors.toList());

        log.info("Unheated surface report was built successfully");
        return new UnheatedSurfaceReport(samples);
    }

}
