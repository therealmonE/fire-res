package io.github.therealmone.fireres.gui.annotation.processor;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.annotation.ModalWindow;
import io.github.therealmone.fireres.gui.controller.ExtendedComponent;
import io.github.therealmone.fireres.gui.model.Logos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import lombok.val;

import java.lang.reflect.Field;

public class ModalWindowAnnotationProcessor implements AnnotationProcessor {

    @Inject
    private Logos logos;

    @Override
    public void process(ExtendedComponent<?> component) {
        if (component.getClass().isAnnotationPresent(ModalWindow.class)) {
            val modalWindowAnnotation = component.getClass().getAnnotation(ModalWindow.class);

            val window = new Stage();

            window.setScene(new Scene((Parent) component.getComponent()));
            window.setTitle(modalWindowAnnotation.title());
            window.setResizable(modalWindowAnnotation.resizable());
            window.initModality(modalWindowAnnotation.modality());
            window.getIcons().add(logos.getLogo512());
            window.setAlwaysOnTop(modalWindowAnnotation.isAlwaysOnTop());

            setWindowField(component, window);
        }
    }

    @SneakyThrows
    private void setWindowField(Object o, Stage window) {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ModalWindow.Window.class)) {
                field.setAccessible(true);
                field.set(o, window);

                return;
            }
        }

        throw new IllegalStateException("Class " + o.getClass().getSimpleName() + " does not have window field");
    }

}
