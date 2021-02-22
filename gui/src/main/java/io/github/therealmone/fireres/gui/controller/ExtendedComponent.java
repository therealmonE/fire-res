package io.github.therealmone.fireres.gui.controller;

import java.util.List;

public interface ExtendedComponent<N> extends Controller {

    N getComponent();

    ExtendedComponent<?> getParent();

    void setParent(ExtendedComponent<?> parentComponent);

    List<ExtendedComponent<?>> getChildren();

    <C extends ExtendedComponent<?>> List<C> getChildren(Class<C> childClass);

}
