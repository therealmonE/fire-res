package io.github.therealmone.fireres.core.pipeline.sample;

import io.github.therealmone.fireres.core.model.Report;
import io.github.therealmone.fireres.core.model.ReportSample;
import io.github.therealmone.fireres.core.pipeline.EnrichType;

public interface SampleEnrichPipeline<R extends Report, S extends ReportSample> {

    void accept(R report, S sample);

    void accept(R report, S sample, EnrichType enrichType);

}
