package io.github.therealmone.fireres.unheated.surface;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.test.GuiceRunner;
import org.junit.runners.model.InitializationError;

public class UnheatedSurfaceGuiceRunner extends GuiceRunner {

    public UnheatedSurfaceGuiceRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AbstractModule getModule() {
        return new UnheatedSurfaceModule();
    }

    @Override
    protected GenerationProperties getProperties() {
        return new TestGenerationProperties();
    }
}
