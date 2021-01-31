package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.controller.GeneralParamsController;
import io.github.therealmone.fireres.gui.controller.SamplesTabPaneController;
import io.github.therealmone.fireres.gui.controller.excess.pressure.ExcessPressureParamsController;

public interface ResetSettingsService {

    void resetSamples(SamplesTabPaneController samplesTabPaneController);

    void resetGeneralParameters(GeneralParamsController generalParamsController);

    void resetExcessPressureParameters(ExcessPressureParamsController excessPressureParamsController);

}
