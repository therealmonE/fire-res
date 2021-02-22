package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.github.therealmone.fireres.gui.annotation.LoadableComponent;
import io.github.therealmone.fireres.gui.annotation.processor.AnnotationProcessor;
import io.github.therealmone.fireres.gui.controller.Controller;
import io.github.therealmone.fireres.gui.controller.ExtendedComponent;
import io.github.therealmone.fireres.gui.service.FxmlLoadService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import lombok.SneakyThrows;
import lombok.val;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;

public class FxmlLoadServiceImpl implements FxmlLoadService {

    @Inject
    private Injector injector;

    @Inject
    private List<AnnotationProcessor> annotationProcessors;

    @Override
    public <C extends ExtendedComponent<?>> C loadComponent(Class<C> componentClass) {
        return loadComponent(componentClass, null);
    }

    @Override
    public <C extends ExtendedComponent<?>> C loadComponent(Class<C> componentClass, ExtendedComponent<?> parent) {
        return loadComponent(componentClass, parent, component -> {
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    @SneakyThrows
    public <C extends ExtendedComponent<?>> C loadComponent(Class<C> componentClass,
                                                            ExtendedComponent<?> parent,
                                                            Consumer<C> preConstructAction) {

        if (!componentClass.isAnnotationPresent(LoadableComponent.class)) {
            throw new IllegalStateException("Component is not annotated with " + LoadableComponent.class.getSimpleName());
        }

        val fxmlAnnotation = componentClass.getAnnotation(LoadableComponent.class);
        val fxml = fxmlAnnotation.value();
        val resource = getClass().getResource(fxml);
        val loader = new FXMLLoader(resource);

        loader.setControllerFactory(injector::getInstance);
        loader.load();

        val component = (C) loader.getController();

        preConstructAction.accept(component);
        initializeComponentHierarchy(component, parent);
        annotationProcessors.forEach(processor -> processor.process(component));
        callPostConstruct(component);

        return component;
    }

    private <C extends ExtendedComponent<?>> void callPostConstruct(C component) {
        component.getChildren().forEach(this::callPostConstruct);
        component.postConstruct();
    }

    @SuppressWarnings({"rawtypes"})
    @SneakyThrows
    private void initializeComponentHierarchy(ExtendedComponent component, ExtendedComponent parent) {
        initializeFxmlComponentHierarchy(component);
        addAdditionalParent(component, parent);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void addAdditionalParent(ExtendedComponent component, ExtendedComponent parent) {
        if (parent != null) {
            parent.getChildren().add(component);
            component.setParent(parent);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void initializeFxmlComponentHierarchy(ExtendedComponent component) throws IllegalAccessException {
        for (Field field : component.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(FXML.class) && field.getName().endsWith("Controller")) {
                field.setAccessible(true);

                val child = (ExtendedComponent<?>) field.get(component);
                child.setParent(component);
                component.getChildren().add(child);
                initializeFxmlComponentHierarchy(child);
            }
        }
    }

}
