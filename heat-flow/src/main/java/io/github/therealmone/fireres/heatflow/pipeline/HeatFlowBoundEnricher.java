package io.github.therealmone.fireres.heatflow.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.EnrichType;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnricher;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;

import java.util.List;

public class HeatFlowBoundEnricher implements ReportEnricher<HeatFlowReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(HeatFlowReport report) {

    }

    @Override
    public boolean supports(EnrichType enrichType) {
        return false;
    }

    @Override
    public List<EnrichType> getAffectedTypes() {
        return null;
    }
}
