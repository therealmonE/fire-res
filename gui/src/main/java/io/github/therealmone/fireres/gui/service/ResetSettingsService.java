package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.controller.GeneralParamsController;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressureParamsController;
import io.github.therealmone.fireres.gui.controller.fire.mode.FireModeParamsController;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlowParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first.FirstGroupParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.second.SecondGroupParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third.ThirdGroupParamsController;

public interface ResetSettingsService {

    void resetSamples(SamplesTabPaneController samplesTabPaneController);

    void resetGeneralParameters(GeneralParamsController generalParamsController);

    void resetExcessPressureParameters(ExcessPressureParamsController excessPressureParamsController);

    void resetHeatFlowParameters(HeatFlowParamsController heatFlowParamsController);

    void resetFireModeParameters(FireModeParamsController fireModeParamsController);

    void resetFunctionParameters(FunctionParamsController functionParamsController);

    void resetFirstThermocoupleGroupParameters(FirstGroupParamsController firstGroupParamsController);

    void resetSecondThermocoupleGroupParameters(SecondGroupParamsController secondGroupParamsController);

    void resetThirdThermocoupleGroupParameters(ThirdGroupParamsController thirdGroupParamsController);
}
