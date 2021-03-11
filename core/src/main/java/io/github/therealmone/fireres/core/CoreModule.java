package io.github.therealmone.fireres.core;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
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
    }

}
