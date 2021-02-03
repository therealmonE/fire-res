package io.github.therealmone.fireres.core.pipeline;

import io.github.therealmone.fireres.core.model.Report;

public interface ReportEnrichPipeline<R extends Report> {

    void accept(R report);

    void accept(R report, ReportEnrichType enrichType);

}
