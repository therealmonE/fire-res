package io.github.therealmone.fireres.core.service.impl;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.core.service.SampleService;

public class SampleServiceImpl implements SampleService {

    @Override
    public Sample create(SampleProperties sampleProperties) {
        return new Sample(sampleProperties);
    }

}
