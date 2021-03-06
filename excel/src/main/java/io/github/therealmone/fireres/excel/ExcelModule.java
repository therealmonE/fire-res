package io.github.therealmone.fireres.excel;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.therealmone.fireres.excel.annotation.ExcessPressure;
import io.github.therealmone.fireres.excel.annotation.FireMode;
import io.github.therealmone.fireres.excel.annotation.HeatFlow;
import io.github.therealmone.fireres.excel.annotation.UnheatedSurface;
import io.github.therealmone.fireres.excel.report.ExcelReportsBuilder;
import io.github.therealmone.fireres.excel.report.ExcessPressureExcelReportsBuilder;
import io.github.therealmone.fireres.excel.report.FireModeExcelReportsBuilder;
import io.github.therealmone.fireres.excel.report.HeatFlowExcelReportsBuilder;
import io.github.therealmone.fireres.excel.report.UnheatedSurfaceExcelReportsBuilder;
import io.github.therealmone.fireres.excel.sheet.ExcelSheetsBuilder;
import io.github.therealmone.fireres.excel.sheet.ExcessPressureSheetsBuilder;
import io.github.therealmone.fireres.excel.sheet.FireModeSheetsBuilder;
import io.github.therealmone.fireres.excel.sheet.HeatFlowSheetsBuilder;
import io.github.therealmone.fireres.excel.sheet.UnheatedSurfaceSheetsBuilder;

import java.util.List;

public class ExcelModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ReportConstructor.class).to(ExcelReportConstructor.class);

        bind(ExcelReportsBuilder.class).annotatedWith(ExcessPressure.class).to(ExcessPressureExcelReportsBuilder.class).in(Singleton.class);
        bind(ExcelReportsBuilder.class).annotatedWith(FireMode.class).to(FireModeExcelReportsBuilder.class).in(Singleton.class);
        bind(ExcelReportsBuilder.class).annotatedWith(UnheatedSurface.class).to(UnheatedSurfaceExcelReportsBuilder.class).in(Singleton.class);
        bind(ExcelReportsBuilder.class).annotatedWith(HeatFlow.class).to(HeatFlowExcelReportsBuilder.class).in(Singleton.class);

        bind(ExcelSheetsBuilder.class).annotatedWith(FireMode.class).to(FireModeSheetsBuilder.class).in(Singleton.class);
        bind(ExcelSheetsBuilder.class).annotatedWith(ExcessPressure.class).to(ExcessPressureSheetsBuilder.class).in(Singleton.class);
        bind(ExcelSheetsBuilder.class).annotatedWith(UnheatedSurface.class).to(UnheatedSurfaceSheetsBuilder.class).in(Singleton.class);
        bind(ExcelSheetsBuilder.class).annotatedWith(HeatFlow.class).to(HeatFlowSheetsBuilder.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    public List<ExcelSheetsBuilder> sheetBuilders(
            @FireMode ExcelSheetsBuilder fireModeSheetsBuilder,
            @ExcessPressure ExcelSheetsBuilder excessPressureSheetsBuilder,
            @HeatFlow ExcelSheetsBuilder heatFlowSheetsBuilder,
            @UnheatedSurface ExcelSheetsBuilder unheatedSurfaceSheetsBuilder
    ) {
        return List.of(
                fireModeSheetsBuilder,
                excessPressureSheetsBuilder,
                heatFlowSheetsBuilder,
                unheatedSurfaceSheetsBuilder
        );
    }

}
