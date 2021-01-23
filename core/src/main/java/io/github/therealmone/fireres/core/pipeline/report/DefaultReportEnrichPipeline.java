package io.github.therealmone.fireres.core.pipeline.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class DefaultReportEnrichPipeline<R extends Report> implements ReportEnrichPipeline<R> {

    private final List<ReportEnricher<R>> enrichers;

    @Override
    public void accept(R report) {
        log.info("Enriching completely {}", report.getClass().getSimpleName());
        enrichers.forEach(enricher -> enricher.enrich(report));
    }

    @Override
    public void accept(R report, EnrichType enrichType) {
        log.info("Enriching {} with {}", report.getClass().getSimpleName(), enrichType.getClass().getSimpleName());
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
