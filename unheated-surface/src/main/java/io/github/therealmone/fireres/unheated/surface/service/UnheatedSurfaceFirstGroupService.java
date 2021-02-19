package io.github.therealmone.fireres.unheated.surface.service;

import io.github.therealmone.fireres.core.service.InterpolationService;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;

public interface UnheatedSurfaceFirstGroupService extends InterpolationService<UnheatedSurfaceReport, Integer> {

    void updateThermocoupleCount(UnheatedSurfaceReport report, Integer thermocoupleCount);

}
