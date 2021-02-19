package io.github.therealmone.fireres.firemode;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.test.GuiceRunner;
import org.junit.runners.model.InitializationError;

public class FireModeGuiceRunner extends GuiceRunner {

    public FireModeGuiceRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AbstractModule getModule() {
        return new FireModeModule();
    }

    @Override
    protected GenerationProperties getProperties() {
        return new TestGenerationProperties();
    }

}
