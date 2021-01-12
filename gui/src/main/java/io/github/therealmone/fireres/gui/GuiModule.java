package io.github.therealmone.fireres.gui;

import com.google.inject.AbstractModule;
import io.github.therealmone.fireres.core.annotation.Time;

public class GuiModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(GraphicalInterface.class);
        bind(Integer.class).annotatedWith(Time.class).toInstance(70);
    }

}
