package io.github.therealmone.fireres.core;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.annotation.BasePressure;
import io.github.therealmone.fireres.core.annotation.EnvironmentTemperature;
import io.github.therealmone.fireres.core.annotation.Time;
import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CoreModule extends AbstractModule {

    private final GenerationProperties properties;

    @Override
    protected void configure() {
        bind(GenerationProperties.class).toInstance(properties);

        bind(Integer.class).annotatedWith(Time.class)
                .toProvider(this::getTime);

        bind(Integer.class).annotatedWith(EnvironmentTemperature.class)
                .toProvider(this::getEnvironmentTemperature);

        bind(Double.class).annotatedWith(BasePressure.class)
                .toProvider(this::getBasePressure);
    }

    private Integer getTime() {
        return Optional.ofNullable(properties.getGeneral())
                .map(GeneralProperties::getTime)
                .orElse(null);
    }

    private Integer getEnvironmentTemperature() {
        return Optional.ofNullable(properties.getGeneral())
                .map(GeneralProperties::getEnvironmentTemperature)
                .orElse(null);
    }

    private Double getBasePressure() {
        return Optional.ofNullable(properties.getGeneral())
                .filter(general -> general.getExcessPressure() != null)
                .map(general -> general.getExcessPressure().getBasePressure())
                .orElse(null);
    }

}
