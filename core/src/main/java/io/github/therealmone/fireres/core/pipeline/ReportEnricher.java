package io.github.therealmone.fireres.core.pipeline;

import io.github.therealmone.fireres.core.model.Report;

import java.util.Collections;
import java.util.List;

public interface ReportEnricher<R extends Report> {

    void enrich(R report);

    boolean supports(EnrichType enrichType);

    default List<EnrichType> getAffectedTypes() {
        return Collections.emptyList();
    }

}
