package io.github.therealmone.fireres.gui.annotation.processor;

import io.github.therealmone.fireres.gui.controller.ExtendedComponent;

public interface AnnotationProcessor {

    void process(ExtendedComponent<?> component);

}
