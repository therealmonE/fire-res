package io.github.therealmone.fireres.heatflow;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.heatflow.pipeline.BoundEnricher;
import io.github.therealmone.fireres.heatflow.pipeline.MeanWithSensorsTemperaturesEnricher;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.service.HeatFlowService;
import io.github.therealmone.fireres.heatflow.service.impl.HeatFlowServiceImpl;

import java.util.List;

public class HeatFlowModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BoundEnricher.class).in(Singleton.class);
        bind(MeanWithSensorsTemperaturesEnricher.class).in(Singleton.class);

        bind(HeatFlowService.class).to(HeatFlowServiceImpl.class);
    }

    @Provides
    @Singleton
    public ReportEnrichPipeline<HeatFlowReport> enrichPipeline(
            BoundEnricher boundEnricher,
            MeanWithSensorsTemperaturesEnricher meanWithSensorsTemperaturesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                boundEnricher,
                meanWithSensorsTemperaturesEnricher
        ));
    }

}
