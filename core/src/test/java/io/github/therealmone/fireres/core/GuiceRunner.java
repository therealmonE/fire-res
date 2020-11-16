package io.github.therealmone.fireres.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.val;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class GuiceRunner extends BlockJUnit4ClassRunner {

    private final Injector injector;

    public GuiceRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        val module = new CoreModule(new TestGenerationProperties());
        this.injector = Guice.createInjector(module);
    }

    @Override
    protected Object createTest() throws Exception {
        val test = super.createTest();
        injector.injectMembers(test);

        return test;
    }
}
