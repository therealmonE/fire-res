package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.config.ExcessPressureParametersConfigurer;
import io.github.therealmone.fireres.gui.config.FireModeParametersConfigurer;
import io.github.therealmone.fireres.gui.config.FunctionParametersConfigurer;
import io.github.therealmone.fireres.gui.config.GeneralParametersConfigurer;
import io.github.therealmone.fireres.gui.config.HeatFlowParametersConfigurer;
import io.github.therealmone.fireres.gui.config.SamplesConfigurer;
import io.github.therealmone.fireres.gui.config.thermocouple.groups.FirstThermocoupleGroupParametersConfigurer;
import io.github.therealmone.fireres.gui.config.thermocouple.groups.SecondThermocoupleGroupParametersConfigurer;
import io.github.therealmone.fireres.gui.config.thermocouple.groups.ThirdThermocoupleGroupParametersConfigurer;
import io.github.therealmone.fireres.gui.controller.GeneralParamsController;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressureParamsController;
import io.github.therealmone.fireres.gui.controller.fire.mode.FireModeParamsController;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlowParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first.FirstGroupParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.second.SecondGroupParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third.ThirdGroupParamsController;
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

    @Inject
    private FireModeParametersConfigurer fireModeParametersConfigurer;

    @Inject
    private FunctionParametersConfigurer functionParametersConfigurer;

    @Inject
    private FirstThermocoupleGroupParametersConfigurer firstThermocoupleGroupParametersConfigurer;

    @Inject
    private SecondThermocoupleGroupParametersConfigurer secondThermocoupleGroupParametersConfigurer;

    @Inject
    private ThirdThermocoupleGroupParametersConfigurer thirdThermocoupleGroupParametersConfigurer;

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

    @Override
    public void resetFireModeParameters(FireModeParamsController fireModeParamsController) {
        fireModeParametersConfigurer.config(fireModeParamsController);
    }

    @Override
    public void resetFunctionParameters(FunctionParamsController functionParamsController) {
        functionParametersConfigurer.config(functionParamsController);
    }

    @Override
    public void resetFirstThermocoupleGroupParameters(FirstGroupParamsController firstGroupParamsController) {
        firstThermocoupleGroupParametersConfigurer.config(firstGroupParamsController);
    }

    @Override
    public void resetSecondThermocoupleGroupParameters(SecondGroupParamsController secondGroupParamsController) {
        secondThermocoupleGroupParametersConfigurer.config(secondGroupParamsController);
    }

    @Override
    public void resetThirdThermocoupleGroupParameters(ThirdGroupParamsController thirdGroupParamsController) {
        thirdThermocoupleGroupParametersConfigurer.config(thirdGroupParamsController);
    }

}
