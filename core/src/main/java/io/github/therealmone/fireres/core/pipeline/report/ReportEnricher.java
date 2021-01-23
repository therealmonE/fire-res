package io.github.therealmone.fireres.core.pipeline.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.pipeline.EnrichType;

import java.util.Collections;
import java.util.List;

public interface ReportEnricher<R extends Report> {

    void enrich(R report);

    boolean supports(EnrichType enrichType);

    default List<EnrichType> getAffectedTypes() {
        return Collections.emptyList();
    }

}
