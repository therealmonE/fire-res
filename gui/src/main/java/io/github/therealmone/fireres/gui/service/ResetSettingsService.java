package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.controller.GeneralParamsController;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressureParamsController;
import io.github.therealmone.fireres.gui.controller.fire.mode.FireModeParamsController;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlowParamsController;

public interface ResetSettingsService {

    void resetSamples(SamplesTabPaneController samplesTabPaneController);

    void resetGeneralParameters(GeneralParamsController generalParamsController);

    void resetExcessPressureParameters(ExcessPressureParamsController excessPressureParamsController);

    void resetHeatFlowParameters(HeatFlowParamsController heatFlowParamsController);

    void resetFireModeParameters(FireModeParamsController fireModeParamsController);
}
