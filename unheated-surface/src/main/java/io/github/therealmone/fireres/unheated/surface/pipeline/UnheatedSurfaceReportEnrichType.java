package io.github.therealmone.fireres.unheated.surface.pipeline;

import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;

public enum UnheatedSurfaceReportEnrichType implements ReportEnrichType {

    FIRST_GROUP_MEAN_BOUND,
    FIRST_GROUP_THERMOCOUPLE_BOUND,
    FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES,

    SECOND_GROUP_THERMOCOUPLE_BOUND,
    SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES,

    THIRD_GROUP_THERMOCOUPLE_BOUND,
    THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES

}
