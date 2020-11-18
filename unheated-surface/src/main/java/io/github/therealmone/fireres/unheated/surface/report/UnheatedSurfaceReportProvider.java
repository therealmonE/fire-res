package io.github.therealmone.fireres.unheated.surface.report;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.unheated.surface.factory.UnheatedSurfaceFactory;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceSample;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class UnheatedSurfaceReportProvider implements Provider<UnheatedSurfaceReport> {

    @Inject
    private GenerationProperties properties;

    @Override
    public UnheatedSurfaceReport get() {
        log.info("Building unheated surface report");
        val factory = new UnheatedSurfaceFactory(properties);

        val samples = IntStream.range(0, properties.getSamples().size())
                .mapToObj(sample -> UnheatedSurfaceSample.builder()
                        .firstGroup(factory.firstThermocoupleGroup(sample))
                        .secondGroup(factory.secondThermocoupleGroup(sample))
                        .thirdGroup(factory.thirdThermocoupleGroup(sample))
                        .build())
                .collect(Collectors.toList());

        log.info("Unheated surface report was built successfully");
        return new UnheatedSurfaceReport(samples);
    }

}
