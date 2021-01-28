package io.github.therealmone.fireres.gui;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.CoreModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.excess.pressure.ExcessPressureModule;
import io.github.therealmone.fireres.firemode.FireModeModule;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;
import io.github.therealmone.fireres.gui.service.SampleCreatorService;
import io.github.therealmone.fireres.gui.service.impl.ResetSettingsServiceImpl;
import io.github.therealmone.fireres.gui.service.impl.SampleCreatorServiceImpl;
import io.github.therealmone.fireres.heatflow.HeatFlowModule;
import io.github.therealmone.fireres.unheated.surface.UnheatedSurfaceModule;

public class GuiModule extends AbstractModule {

    private final GenerationProperties generationProperties = new GenerationProperties();

    @Override
    protected void configure() {
        install(new CoreModule(generationProperties));
        install(new FireModeModule());
        install(new ExcessPressureModule());
        install(new UnheatedSurfaceModule());
        install(new HeatFlowModule());

        bind(GraphicalInterface.class);
        bind(SampleCreatorService.class).to(SampleCreatorServiceImpl.class);
        bind(ResetSettingsService.class).to(ResetSettingsServiceImpl.class);
    }

}
