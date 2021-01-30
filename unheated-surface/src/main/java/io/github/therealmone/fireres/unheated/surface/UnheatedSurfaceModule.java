package io.github.therealmone.fireres.unheated.surface;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.core.pipeline.report.DefaultReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.report.ReportEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.DefaultSampleEnrichPipeline;
import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichPipeline;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceSample;
import io.github.therealmone.fireres.unheated.surface.pipeline.report.SamplesEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.sample.firstgroup.SampleFirstGroupMeanBoundEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.sample.firstgroup.SampleFirstGroupMeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.sample.firstgroup.SampleFirstGroupThermocoupleBoundEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.sample.secondgroup.SampleSecondGroupMeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.sample.secondgroup.SampleSecondGroupThermocoupleBoundEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.sample.thirdgroup.SampleThirdGroupMeanWithThermocoupleTemperaturesEnricher;
import io.github.therealmone.fireres.unheated.surface.pipeline.sample.thirdgroup.SampleThirdGroupThermocoupleBoundEnricher;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReportProvider;

import java.util.List;

public class UnheatedSurfaceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UnheatedSurfaceReport.class)
                .toProvider(UnheatedSurfaceReportProvider.class)
                .in(Singleton.class);
    }

    @Provides
    @Singleton
    public ReportEnrichPipeline<UnheatedSurfaceReport> enrichPipeline(
            SamplesEnricher samplesEnricher
    ) {
        return new DefaultReportEnrichPipeline<>(List.of(
                samplesEnricher
        ));
    }

    @Provides
    @Singleton
    public SampleEnrichPipeline<UnheatedSurfaceReport, UnheatedSurfaceSample> sampleEnrichPipeline(

            SampleFirstGroupMeanBoundEnricher sampleFirstGroupMeanBoundEnricher,
            SampleFirstGroupThermocoupleBoundEnricher sampleFirstGroupThermocoupleBoundEnricher,
            SampleFirstGroupMeanWithThermocoupleTemperaturesEnricher sampleFirstGroupMeanWithThermocoupleTemperaturesEnricher,

            SampleSecondGroupThermocoupleBoundEnricher sampleSecondGroupThermocoupleBoundEnricher,
            SampleSecondGroupMeanWithThermocoupleTemperaturesEnricher sampleSecondGroupMeanWithThermocoupleTemperaturesEnricher,

            SampleThirdGroupThermocoupleBoundEnricher sampleThirdGroupThermocoupleBoundEnricher,
            SampleThirdGroupMeanWithThermocoupleTemperaturesEnricher sampleThirdGroupMeanWithThermocoupleTemperaturesEnricher
    ) {
        return new DefaultSampleEnrichPipeline<>(List.of(
                sampleFirstGroupMeanBoundEnricher,
                sampleFirstGroupThermocoupleBoundEnricher,
                sampleFirstGroupMeanWithThermocoupleTemperaturesEnricher,
                sampleSecondGroupThermocoupleBoundEnricher,
                sampleSecondGroupMeanWithThermocoupleTemperaturesEnricher,
                sampleThirdGroupThermocoupleBoundEnricher,
                sampleThirdGroupMeanWithThermocoupleTemperaturesEnricher
        ));
    }

}
