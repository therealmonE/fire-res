package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.controller.GeneralParamsController;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressureParamsController;
import io.github.therealmone.fireres.gui.controller.fire.mode.FireModeParamsController;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlowParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.first.thermocouple.group.FirstThermocoupleGroupParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.second.thermocouple.group.SecondThermocoupleGroupParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.third.thermocouple.group.ThirdThermocoupleGroupParamsController;

public interface ResetSettingsService {

    void resetSamples(SamplesTabPaneController samplesTabPaneController);

    void resetGeneralParameters(GeneralParamsController generalParamsController);

    void resetExcessPressureParameters(ExcessPressureParamsController excessPressureParamsController);

    void resetHeatFlowParameters(HeatFlowParamsController heatFlowParamsController);

    void resetFireModeParameters(FireModeParamsController fireModeParamsController);

    void resetFunctionParameters(FunctionParamsController functionParamsController);

    void resetFirstThermocoupleGroupParameters(FirstThermocoupleGroupParamsController firstThermocoupleGroupParamsController);

    void resetSecondThermocoupleGroupParameters(SecondThermocoupleGroupParamsController secondThermocoupleGroupParamsController);

    void resetThirdThermocoupleGroupParameters(ThirdThermocoupleGroupParamsController thirdThermocoupleGroupParamsController);
}
