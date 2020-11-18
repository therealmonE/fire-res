package io.github.therealmone.fireres.excel;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.CoreModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.excel.annotation.ExcessPressure;
import io.github.therealmone.fireres.excel.annotation.FireMode;
import io.github.therealmone.fireres.excel.model.ExcelReports;
import io.github.therealmone.fireres.excel.report.ExcessPressureExcelReportsProvider;
import io.github.therealmone.fireres.excel.report.FireModeExcelReportsProvider;
import io.github.therealmone.fireres.excel.sheet.ExcelSheet;
import io.github.therealmone.fireres.excel.sheet.ExcessPressureSheet;
import io.github.therealmone.fireres.excel.sheet.FireModeSheet;
import io.github.therealmone.fireres.excess.pressure.ExcessPressureModule;
import io.github.therealmone.fireres.firemode.FireModeModule;
import io.github.therealmone.fireres.unheated.surface.UnheatedSurfaceModule;

public class ExcelModule extends AbstractModule {

    private final GenerationProperties properties;

    public ExcelModule(GenerationProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        install(new CoreModule(properties));
        install(new FireModeModule());
        install(new ExcessPressureModule());
        install(new UnheatedSurfaceModule());

        bind(ReportConstructor.class).to(ExcelReportConstructor.class);

        bind(ExcelSheet.class).annotatedWith(FireMode.class).to(FireModeSheet.class);
        bind(ExcelReports.class).annotatedWith(FireMode.class)
                .toProvider(FireModeExcelReportsProvider.class);

        bind(ExcelSheet.class).annotatedWith(ExcessPressure.class).to(ExcessPressureSheet.class);
        bind(ExcelReports.class).annotatedWith(ExcessPressure.class)
                .toProvider(ExcessPressureExcelReportsProvider.class);
    }
}
