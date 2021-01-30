package io.github.therealmone.fireres.unheated.surface.pipeline.sample;

import io.github.therealmone.fireres.core.pipeline.sample.SampleEnrichType;

public enum UnheatedSurfaceSampleEnrichType implements SampleEnrichType {

    SAMPLE_FIRST_GROUP_MEAN_BOUND,
    SAMPLE_FIRST_GROUP_THERMOCOUPLE_BOUND,
    SAMPLE_FIRST_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES,

    SAMPLE_SECOND_GROUP_THERMOCOUPLE_BOUND,
    SAMPLE_SECOND_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES,

    SAMPLE_THIRD_GROUP_THERMOCOUPLE_BOUND,
    SAMPLE_THIRD_GROUP_MEAN_WITH_THERMOCOUPLE_TEMPERATURES,

}
