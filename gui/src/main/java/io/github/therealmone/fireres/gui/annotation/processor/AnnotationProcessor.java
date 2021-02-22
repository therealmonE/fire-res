package io.github.therealmone.fireres.gui.annotation.processor;

import io.github.therealmone.fireres.gui.controller.ExtendedComponent;
import javafx.scene.Node;

public interface AnnotationProcessor {

    void process(ExtendedComponent<?> component);

}
