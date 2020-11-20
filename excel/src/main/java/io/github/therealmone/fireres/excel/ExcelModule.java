package io.github.therealmone.fireres.excel;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import io.github.therealmone.fireres.core.CoreModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.config.ReportType;
import io.github.therealmone.fireres.excel.annotation.ExcessPressure;
import io.github.therealmone.fireres.excel.annotation.FireMode;
import io.github.therealmone.fireres.excel.annotation.UnheatedSurface;
import io.github.therealmone.fireres.excel.report.ExcelReport;
import io.github.therealmone.fireres.excel.report.ExcessPressureExcelReportsProvider;
import io.github.therealmone.fireres.excel.report.FireModeExcelReportsProvider;
import io.github.therealmone.fireres.excel.report.UnheatedSurfaceExcelReportsProvider;
import io.github.therealmone.fireres.excel.sheet.ExcelSheet;
import io.github.therealmone.fireres.excel.sheet.ExcessPressureSheetsProvider;
import io.github.therealmone.fireres.excel.sheet.FireModeSheetsProvider;
import io.github.therealmone.fireres.excel.sheet.UnheatedSurfaceSheetsProvider;
import io.github.therealmone.fireres.excess.pressure.ExcessPressureModule;
import io.github.therealmone.fireres.firemode.FireModeModule;
import io.github.therealmone.fireres.unheated.surface.UnheatedSurfaceModule;
import lombok.val;

import java.util.List;
import java.util.Map;

public class ExcelModule extends AbstractModule {

    private final GenerationProperties properties;

    public ExcelModule(GenerationProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void configure() {
        install(new CoreModule(properties));

        val includedReports = properties.getGeneral().getIncludedReports();

        if (includedReports.contains(ReportType.FIRE_MODE)) {
            configureFireMode();
        }

        if (includedReports.contains(ReportType.EXCESS_PRESSURE)) {
            configureExcessPressure();
        }

        if (includedReports.contains(ReportType.UNHEATED_SURFACE)) {
            configureUnheatedSurface();
        }

        bind(ReportConstructor.class).to(ExcelReportConstructor.class);
    }

    private void configureFireMode() {
        install(new FireModeModule());

        bind(new TypeLiteral<List<ExcelSheet>>(){})
                .annotatedWith(FireMode.class)
                .toProvider(FireModeSheetsProvider.class);

        bind(new TypeLiteral<List<ExcelReport>>(){})
                .annotatedWith(FireMode.class)
                .toProvider(FireModeExcelReportsProvider.class);
    }

    private void configureExcessPressure() {
        install(new ExcessPressureModule());

        bind(new TypeLiteral<List<ExcelSheet>>(){})
                .annotatedWith(ExcessPressure.class)
                .toProvider(ExcessPressureSheetsProvider.class);

        bind(new TypeLiteral<List<ExcelReport>>(){})
                .annotatedWith(ExcessPressure.class)
                .toProvider(ExcessPressureExcelReportsProvider.class);
    }

    private void configureUnheatedSurface() {
        install(new UnheatedSurfaceModule());

        bind(new TypeLiteral<List<ExcelSheet>>(){})
                .annotatedWith(UnheatedSurface.class)
                .toProvider(UnheatedSurfaceSheetsProvider.class);

        bind(new TypeLiteral<Map<Integer, List<ExcelReport>>>(){})
                .annotatedWith(UnheatedSurface.class)
                .toProvider(UnheatedSurfaceExcelReportsProvider.class);
    }
}
