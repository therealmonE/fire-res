package io.github.therealmone.fireres.core.pipeline;

import io.github.therealmone.fireres.core.model.Report;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.List;

@RequiredArgsConstructor
public class DefaultReportEnrichPipeline<R extends Report> implements ReportEnrichPipeline<R> {

    private final List<ReportEnricher<R>> enrichers;

    @Override
    public void accept(R report) {
        enrichers.forEach(enricher -> enricher.enrich(report));
    }

    @Override
    public void accept(R report, EnrichType enrichType) {
        val enricher = lookUpEnricher(enrichType);

        enricher.enrich(report);

        enricher.getAffectedTypes().forEach(affectedType -> accept(report, affectedType));
    }

    private ReportEnricher<R> lookUpEnricher(EnrichType enrichType) {
        return enrichers.stream()
                .filter(enricher -> enricher.supports(enrichType))
                .findFirst()
                .orElseThrow();
    }
}
