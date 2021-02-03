package io.github.therealmone.fireres.core.service;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.Sample;

public interface SampleService {

    Sample create(SampleProperties sampleProperties);

}
