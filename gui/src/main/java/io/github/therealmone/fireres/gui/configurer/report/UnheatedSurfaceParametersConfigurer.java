package io.github.therealmone.fireres.gui.configurer.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurface;
import io.github.therealmone.fireres.gui.preset.Preset;

public class UnheatedSurfaceParametersConfigurer extends AbstractReportParametersConfigurer<UnheatedSurface> {

    @Inject
    private UnheatedSurfaceFirstGroupConfigurer firstGroupConfigurer;

    @Inject
    private UnheatedSurfaceSecondGroupConfigurer secondGroupConfigurer;

    @Inject
    private UnheatedSurfaceThirdGroupConfigurer thirdGroupConfigurer;

    @Override
    public void config(UnheatedSurface unheatedSurface, Preset preset) {
        firstGroupConfigurer.config(unheatedSurface.getFirstGroup(), preset);
        secondGroupConfigurer.config(unheatedSurface.getSecondGroup(), preset);
        thirdGroupConfigurer.config(unheatedSurface.getThirdGroup(), preset);
    }

}
