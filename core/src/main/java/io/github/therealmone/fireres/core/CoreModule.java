package io.github.therealmone.fireres.core;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.annotation.BasePressure;
import io.github.therealmone.fireres.core.annotation.EnvironmentTemperature;
import io.github.therealmone.fireres.core.annotation.Time;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreModule extends AbstractModule {

    private final GenerationProperties properties;

    @Override
    protected void configure() {
        bind(GenerationProperties.class).toInstance(properties);

        bind(Integer.class).annotatedWith(Time.class).toInstance(properties.getGeneral().getTime());

        bind(Integer.class).annotatedWith(EnvironmentTemperature.class)
                .toInstance(properties.getGeneral().getEnvironmentTemperature());

        bind(Double.class).annotatedWith(BasePressure.class)
                .toInstance(properties.getGeneral().getExcessPressure().getBasePressure());
    }

}
