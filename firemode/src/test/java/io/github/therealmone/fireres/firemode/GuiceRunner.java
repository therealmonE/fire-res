package io.github.therealmone.fireres.firemode;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.therealmone.fireres.core.CoreModule;
import lombok.val;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class GuiceRunner extends BlockJUnit4ClassRunner {

    private final Injector injector;

    public GuiceRunner(Class<?> testClass) throws InitializationError {
        super(testClass);

        this.injector = Guice.createInjector(
                new FireModeModule(),
                new CoreModule(new TestGenerationProperties()));
    }

    @Override
    protected Object createTest() throws Exception {
        val test = super.createTest();
        injector.injectMembers(test);

        return test;
    }
}
