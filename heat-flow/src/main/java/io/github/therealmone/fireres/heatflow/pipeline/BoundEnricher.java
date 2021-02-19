package io.github.therealmone.fireres.heatflow.pipeline;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;
import io.github.therealmone.fireres.core.pipeline.ReportEnricher;
import io.github.therealmone.fireres.heatflow.generator.HeatFlowBoundGenerator;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

import static io.github.therealmone.fireres.heatflow.pipeline.HeatFlowReportEnrichType.BOUND;
import static io.github.therealmone.fireres.heatflow.pipeline.HeatFlowReportEnrichType.MEAN_WITH_SENSORS_TEMPERATURES;

@Slf4j
public class BoundEnricher implements ReportEnricher<HeatFlowReport> {

    @Inject
    private GenerationProperties generationProperties;

    @Override
    public void enrich(HeatFlowReport report) {
        val time = generationProperties.getGeneral().getTime();

        val bound = new HeatFlowBoundGenerator(time, (int) (report.getProperties().getBound() * 1000))
                .generate();

        report.setBound(bound);
    }

    @Override
    public boolean supports(ReportEnrichType enrichType) {
        return BOUND.equals(enrichType);
    }

    @Override
    public List<ReportEnrichType> getAffectedTypes() {
        return List.of(MEAN_WITH_SENSORS_TEMPERATURES);
    }

}
