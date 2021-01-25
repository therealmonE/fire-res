package io.github.therealmone.fireres.core.pipeline.report;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.pipeline.EnrichType;

public interface ReportEnrichPipeline<R extends Report> {

    void accept(R report);

    void accept(R report, EnrichType enrichType);

}
