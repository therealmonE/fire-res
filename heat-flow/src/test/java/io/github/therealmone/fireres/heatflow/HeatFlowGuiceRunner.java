package io.github.therealmone.fireres.heatflow;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.core.test.GuiceRunner;
import org.junit.runners.model.InitializationError;

public class HeatFlowGuiceRunner extends GuiceRunner {

    public HeatFlowGuiceRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AbstractModule getModule() {
        return new HeatFlowModule();
    }

    @Override
    protected GenerationProperties getProperties() {
        return new TestGenerationProperties();
    }
}
