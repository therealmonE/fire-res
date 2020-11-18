package io.github.therealmone.fireres.excess.pressure;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.excess.pressure.factory.ExcessPressureFactory;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReport;
import io.github.therealmone.fireres.excess.pressure.report.ExcessPressureReportProvider;

public class ExcessPressureModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExcessPressureReport.class).toProvider(ExcessPressureReportProvider.class);
        bind(ExcessPressureFactory.class);
    }

}
