package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.config.ExcessPressureParametersConfigurer;
import io.github.therealmone.fireres.gui.config.GeneralParametersConfigurer;
import io.github.therealmone.fireres.gui.config.HeatFlowParametersConfigurer;
import io.github.therealmone.fireres.gui.config.SamplesConfigurer;
import io.github.therealmone.fireres.gui.controller.GeneralParamsController;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressureParamsController;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlowParamsController;
import io.github.therealmone.fireres.gui.service.ResetSettingsService;

public class ResetSettingsServiceImpl implements ResetSettingsService {

    @Inject
    private SamplesConfigurer samplesConfigurer;

    @Inject
    private GeneralParametersConfigurer generalParametersConfigurer;

    @Inject
    private ExcessPressureParametersConfigurer excessPressureParametersConfigurer;

    @Inject
    private HeatFlowParametersConfigurer heatFlowParametersConfigurer;

    @Override
    public void resetSamples(SamplesTabPaneController samplesTabPaneController) {
        samplesConfigurer.config(samplesTabPaneController);
    }

    @Override
    public void resetGeneralParameters(GeneralParamsController generalParamsController) {
        generalParametersConfigurer.config(generalParamsController);
    }

    @Override
    public void resetExcessPressureParameters(ExcessPressureParamsController excessPressureParamsController) {
        excessPressureParametersConfigurer.config(excessPressureParamsController);
    }

    @Override
    public void resetHeatFlowParameters(HeatFlowParamsController heatFlowParamsController) {
        heatFlowParametersConfigurer.config(heatFlowParamsController);
    }

}
