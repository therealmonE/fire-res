package io.github.therealmone.fireres.core.pipeline.sample;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.ReportSample;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.List;

@RequiredArgsConstructor
public class DefaultSampleEnrichPipeline<R extends Report, S extends ReportSample> implements SampleEnrichPipeline<R, S> {

    private final List<SampleEnricher<R, S>> enrichers;

    @Override
    public void accept(R report, S sample) {
        enrichers.forEach(enricher -> enricher.enrich(report, sample));
    }

    @Override
    public void accept(R report, S sample, SampleEnrichType enrichType) {
        val enricher = lookUpEnricher(enrichType);

        enricher.enrich(report, sample);

        enricher.getAffectedTypes().forEach(affectedType -> accept(report, sample, affectedType));
    }

    private SampleEnricher<R, S> lookUpEnricher(SampleEnrichType enrichType) {
        return enrichers.stream()
                .filter(enricher -> enricher.supports(enrichType))
                .findFirst()
                .orElseThrow();
    }
}
