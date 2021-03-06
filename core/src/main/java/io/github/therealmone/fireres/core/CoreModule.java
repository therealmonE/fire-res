package io.github.therealmone.fireres.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.rits.cloning.Cloner;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.service.FunctionsGenerationService;
import io.github.therealmone.fireres.core.service.ValidationService;
import io.github.therealmone.fireres.core.service.impl.FunctionsGenerationServiceImpl;
import io.github.therealmone.fireres.core.service.impl.ValidationServiceImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreModule extends AbstractModule {

    private final GenerationProperties properties;

    @Override
    protected void configure() {
        bind(GenerationProperties.class).toInstance(properties);
        bind(FunctionsGenerationService.class).to(FunctionsGenerationServiceImpl.class).in(Singleton.class);
        bind(ValidationService.class).to(ValidationServiceImpl.class).in(Singleton.class);
        bind(Cloner.class).in(Singleton.class);
        bind(ObjectMapper.class).toInstance(configureObjectMapper());
    }

    @Singleton
    private ObjectMapper configureObjectMapper() {
        return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

}
