package io.github.therealmone.fireres.excel;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
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
        install(new FireModeModule(properties));
        install(new ExcessPressureModule(properties));
        install(new UnheatedSurfaceModule(properties));

        bind(ReportConstructor.class).to(ExcelReportConstructor.class);
        bind(FireModeSheet.class);
        bind(ExcessPressureSheet.class);
    }
}
