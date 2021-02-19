package io.github.therealmone.fireres.core.test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.therealmone.fireres.core.CoreModule;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import lombok.val;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public abstract class GuiceRunner extends BlockJUnit4ClassRunner {

    private final Injector injector;

    public GuiceRunner(Class<?> testClass) throws InitializationError {
        super(testClass);

        this.injector = Guice.createInjector(getModule(), new CoreModule(getProperties()));
    }

    @Override
    protected Object createTest() throws Exception {
        val test = super.createTest();
        injector.injectMembers(test);

        return test;
    }

    protected abstract AbstractModule getModule();

    protected abstract GenerationProperties getProperties();
}
