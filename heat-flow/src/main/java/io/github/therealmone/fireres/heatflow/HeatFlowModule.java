package io.github.therealmone.fireres.heatflow;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.heatflow.factory.HeatFlowFactory;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReport;
import io.github.therealmone.fireres.heatflow.report.HeatFlowReportProvider;

public class HeatFlowModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HeatFlowReport.class).toProvider(HeatFlowReportProvider.class);
        bind(HeatFlowFactory.class);
    }
}
