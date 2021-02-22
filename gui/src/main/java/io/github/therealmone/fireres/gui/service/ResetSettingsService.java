package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.controller.common.FunctionParams;
import io.github.therealmone.fireres.gui.controller.common.GeneralParams;
import io.github.therealmone.fireres.gui.controller.common.SamplesTabPane;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressureParams;
import io.github.therealmone.fireres.gui.controller.fire.mode.FireModeParams;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlowParams;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first.FirstGroupParams;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.second.SecondGroupParams;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third.ThirdGroupParams;

public interface ResetSettingsService {

    void resetSamples(SamplesTabPane samplesTabPane);

    void resetGeneralParameters(GeneralParams generalParams);

    void resetExcessPressureParameters(ExcessPressureParams excessPressureParams);

    void resetHeatFlowParameters(HeatFlowParams heatFlowParams);

    void resetFireModeParameters(FireModeParams fireModeParams);

    void resetFunctionParameters(FunctionParams functionParams);

    void resetFirstThermocoupleGroupParameters(FirstGroupParams firstGroupParams);

    void resetSecondThermocoupleGroupParameters(SecondGroupParams secondGroupParams);

    void resetThirdThermocoupleGroupParameters(ThirdGroupParams thirdGroupParams);
}
