package io.github.therealmone.fireres.gui.service;

import io.github.therealmone.fireres.gui.controller.ExtendedComponent;

import java.util.function.Consumer;

public interface FxmlLoadService {

    <C extends ExtendedComponent<?>> C loadComponent(Class<C> componentClass);

    <C extends ExtendedComponent<?>> C loadComponent(Class<C> componentClass, ExtendedComponent<?> parent);

    <C extends ExtendedComponent<?>> C loadComponent(Class<C> componentClass,
                                                     ExtendedComponent<?> parent,
                                                     Consumer<C> preConstructAction);

}
