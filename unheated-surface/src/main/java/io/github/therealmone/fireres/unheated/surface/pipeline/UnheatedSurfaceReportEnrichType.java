package io.github.therealmone.fireres.unheated.surface.pipeline;

import io.github.therealmone.fireres.core.pipeline.ReportEnrichType;

public enum UnheatedSurfaceReportEnrichType implements ReportEnrichType {

    FIRST_GROUP_MAX_ALLOWED_MEAN_TEMPERATURE,
    FIRST_GROUP_MAX_ALLOWED_THERMOCOUPLE_TEMPERATURE,
    FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES,

    SECOND_GROUP_MAX_ALLOWED_TEMPERATURE,
    SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES,

    THIRD_GROUP_MAX_ALLOWED_TEMPERATURE,
    THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES

}
