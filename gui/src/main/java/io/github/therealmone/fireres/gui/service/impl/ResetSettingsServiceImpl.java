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
import io.github.therealmone.fireres.gui.controller.common.FunctionParams;
import io.github.therealmone.fireres.gui.controller.common.GeneralParams;
import io.github.therealmone.fireres.gui.controller.common.SamplesTabPane;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressureParams;
import io.github.therealmone.fireres.gui.controller.fire.mode.FireModeParams;
import io.github.therealmone.fireres.gui.controller.heat.flow.HeatFlowParams;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first.FirstGroupParams;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.second.SecondGroupParams;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.third.ThirdGroupParams;
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
    public void resetSamples(SamplesTabPane samplesTabPane) {
        samplesConfigurer.config(samplesTabPane);
    }

    @Override
    public void resetGeneralParameters(GeneralParams generalParams) {
        generalParametersConfigurer.config(generalParams);
    }

    @Override
    public void resetExcessPressureParameters(ExcessPressureParams excessPressureParams) {
        excessPressureParametersConfigurer.config(excessPressureParams);
    }

    @Override
    public void resetHeatFlowParameters(HeatFlowParams heatFlowParams) {
        heatFlowParametersConfigurer.config(heatFlowParams);
    }

    @Override
    public void resetFireModeParameters(FireModeParams fireModeParams) {
        fireModeParametersConfigurer.config(fireModeParams);
    }

    @Override
    public void resetFunctionParameters(FunctionParams functionParams) {
        functionParametersConfigurer.config(functionParams);
    }

    @Override
    public void resetFirstThermocoupleGroupParameters(FirstGroupParams firstGroupParams) {
        firstThermocoupleGroupParametersConfigurer.config(firstGroupParams);
    }

    @Override
    public void resetSecondThermocoupleGroupParameters(SecondGroupParams secondGroupParams) {
        secondThermocoupleGroupParametersConfigurer.config(secondGroupParams);
    }

    @Override
    public void resetThirdThermocoupleGroupParameters(ThirdGroupParams thirdGroupParams) {
        thirdThermocoupleGroupParametersConfigurer.config(thirdGroupParams);
    }

}
