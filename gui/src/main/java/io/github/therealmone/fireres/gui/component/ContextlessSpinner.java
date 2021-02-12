package io.github.therealmone.fireres.gui.component;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.scene.control.Spinner;

import static javafx.scene.input.ContextMenuEvent.CONTEXT_MENU_REQUESTED;

public class ContextlessSpinner<N extends Number> extends Spinner<N> {

    public ContextlessSpinner(@NamedArg("min") int min,
                              @NamedArg("max") int max,
                              @NamedArg("initialValue") int initialValue) {
        super(min, max, initialValue);

        this.addEventFilter(CONTEXT_MENU_REQUESTED, Event::consume);
    }

    public ContextlessSpinner(@NamedArg("min") int min,
                              @NamedArg("max") int max,
                              @NamedArg("initialValue") int initialValue,
                              @NamedArg("amountToStepBy") int amountToStepBy) {
        super(min, max, initialValue, amountToStepBy);

        this.addEventFilter(CONTEXT_MENU_REQUESTED, Event::consume);
    }

    public ContextlessSpinner(@NamedArg("min") double min,
                              @NamedArg("max") double max,
                              @NamedArg("initialValue") double initialValue) {
        super(min, max, initialValue);

        this.addEventFilter(CONTEXT_MENU_REQUESTED, Event::consume);
    }

    public ContextlessSpinner(@NamedArg("min") double min,
                              @NamedArg("max") double max,
                              @NamedArg("initialValue") double initialValue,
                              @NamedArg("amountToStepBy") double amountToStepBy) {
        super(min, max, initialValue, amountToStepBy);

        this.addEventFilter(CONTEXT_MENU_REQUESTED, Event::consume);
    }

    public ContextlessSpinner() {
        super();

        this.addEventFilter(CONTEXT_MENU_REQUESTED, Event::consume);
    }
}
