package io.github.therealmone.fireres.excess.pressure;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.test.GuiceRunner;
import org.junit.runners.model.InitializationError;

public class ExcessPressureGuiceRunner extends GuiceRunner {

    public ExcessPressureGuiceRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AbstractModule getModule() {
        return new ExcessPressureModule();
    }

    @Override
    protected GenerationProperties getProperties() {
        return new TestGenerationProperties();
    }
}
