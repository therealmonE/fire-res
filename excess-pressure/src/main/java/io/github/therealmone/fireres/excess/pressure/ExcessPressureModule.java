package io.github.therealmone.fireres.excess.pressure;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.excess.pressure.pipeline.MaxAllowedPressureEnricher;
import io.github.therealmone.fireres.excess.pressure.pipeline.MinAllowedPressureEnricher;
import io.github.therealmone.fireres.excess.pressure.pipeline.SamplesPressureEnricher;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReportProvider;

import java.util.List;

public class ExcessPressureModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExcessPressureReport.class)
                .toProvider(ExcessPressureReportProvider.class)
                .in(Singleton.class);
    }

    @Provides
    public ReportEnrichPipeline<ExcessPressureReport> enrichPipeline(
            MinAllowedPressureEnricher minAllowedPressureEnricher,
            MaxAllowedPressureEnricher maxAllowedPressureEnricher,
            SamplesPressureEnricher samplesPressureEnricher) {

        return new DefaultReportEnrichPipeline<>(List.of(
                minAllowedPressureEnricher,
                maxAllowedPressureEnricher,
                samplesPressureEnricher));
    }

}
