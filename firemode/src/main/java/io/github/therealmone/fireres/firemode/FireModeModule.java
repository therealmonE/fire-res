package io.github.therealmone.fireres.firemode;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.firemode.factory.FireModeFactory;
import io.github.therealmone.fireres.firemode.report.FireModeReport;
import io.github.therealmone.fireres.firemode.report.FireModeReportProvider;

public class FireModeModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FireModeReport.class).toProvider(FireModeReportProvider.class);
        bind(FireModeFactory.class);
    }

}
