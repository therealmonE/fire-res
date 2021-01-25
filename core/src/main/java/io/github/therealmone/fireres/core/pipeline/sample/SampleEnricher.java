package io.github.therealmone.fireres.core.pipeline.sample;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.ReportSample;
import io.github.therealmone.fireres.core.pipeline.EnrichType;

import java.util.Collections;
import java.util.List;

public interface SampleEnricher<R extends Report, S extends ReportSample> {

    void enrich(R report, S sample);

    boolean supports(EnrichType enrichType);

    default List<EnrichType> getAffectedTypes() {
        return Collections.emptyList();
    }

}
