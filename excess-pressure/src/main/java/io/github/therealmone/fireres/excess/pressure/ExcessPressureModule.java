package io.github.therealmone.fireres.excess.pressure;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.pipeline.DefaultReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.ReportEnrichPipeline;
import io.github.therealmone.fireres.excess.pressure.pipeline.BasePressureEnricher;
import io.github.therealmone.fireres.excess.pressure.pipeline.MaxAllowedPressureEnricher;
import io.github.therealmone.fireres.excess.pressure.pipeline.MinAllowedPressureEnricher;
import io.github.therealmone.fireres.excess.pressure.pipeline.PressureEnricher;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.service.ExcessPressureService;
import io.github.therealmone.fireres.excess.pressure.service.impl.ExcessPressureServiceImpl;

import java.util.List;

public class ExcessPressureModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BasePressureEnricher.class).in(Singleton.class);
        bind(MinAllowedPressureEnricher.class).in(Singleton.class);
        bind(MaxAllowedPressureEnricher.class).in(Singleton.class);
        bind(PressureEnricher.class).in(Singleton.class);

        bind(ExcessPressureService.class).to(ExcessPressureServiceImpl.class);
    }

    @Provides
    @Singleton
    public ReportEnrichPipeline<ExcessPressureReport> enrichPipeline(
            BasePressureEnricher basePressureEnricher,
            MinAllowedPressureEnricher minAllowedPressureEnricher,
            MaxAllowedPressureEnricher maxAllowedPressureEnricher,
            PressureEnricher pressureEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                basePressureEnricher,
                minAllowedPressureEnricher,
                maxAllowedPressureEnricher,
                pressureEnricher
        ));
    }

}
