package io.github.therealmone.fireres.excel;

import io.github.therealmone.fireres.core.CoreModule;
import io.github.therealmone.fireres.excess.pressure.ExcessPressureModule;
import io.github.therealmone.fireres.firemode.FireModeModule;
import io.github.therealmone.fireres.heatflow.HeatFlowModule;
import io.github.therealmone.fireres.unheated.surface.UnheatedSurfaceModule;

public class TestModule extends ExcelModule {

    @Override
    protected void configure() {
        super.configure();

        install(new CoreModule(new TestGenerationProperties()));
        install(new FireModeModule());
        install(new ExcessPressureModule());
        install(new HeatFlowModule());
        install(new UnheatedSurfaceModule());
    }
}
