package io.github.therealmone.fireres.gui.configurer.report;

import com.google.inject.Inject;
import com.rits.cloning.Cloner;
import io.github.therealmone.fireres.gui.controller.unheated.surface.groups.first.FirstGroup;
import io.github.therealmone.fireres.gui.preset.Preset;
import io.github.therealmone.fireres.unheated.surface.config.UnheatedSurfaceProperties;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import lombok.val;

public class UnheatedSurfaceFirstGroupConfigurer extends AbstractReportParametersConfigurer<FirstGroup> {

    private static final Integer THERMOCOUPLES_NUMBER_MIN = 1;
    private static final Integer THERMOCOUPLES_NUMBER_MAX = 100;

    @Inject
    private Cloner cloner;

    @Override
    public void config(FirstGroup firstGroup, Preset preset) {
        val sampleProperties = firstGroup.getSample().getSampleProperties();
        val presetProperties = cloner.deepClone(preset.getProperties(UnheatedSurfaceProperties.class).getFirstGroup());

        sampleProperties.getReportPropertiesByClass(UnheatedSurfaceProperties.class)
                .orElseThrow()
                .setFirstGroup(presetProperties);

        resetThermocouplesCount(
                firstGroup.getFirstGroupParams().getThermocouples(),
                presetProperties.getThermocoupleCount());

        resetFunctionParameters(firstGroup.getFunctionParams(), presetProperties.getFunctionForm());
    }

    private void resetThermocouplesCount(Spinner<Integer> thermocouplesCount, Integer thermocouplesCountValue) {
        thermocouplesCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                THERMOCOUPLES_NUMBER_MIN,
                THERMOCOUPLES_NUMBER_MAX,
                thermocouplesCountValue));
    }

}
